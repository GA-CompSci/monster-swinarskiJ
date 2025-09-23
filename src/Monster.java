import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * A simple, well-documented Monster class suitable for small games or exercises.
 * <p>
 * Features:
 * - Basic stats (name, level, max/current HP, attack, defense, exp)
 * - Methods to attack another Monster, take damage, heal, level up, and check alive status
 * - Implements Serializable and Comparable (by level then name)
 * - Defensive programming: ensures HP bounds and non-negative stats
 *
 * You can adapt fields / methods to match your project's exact API.
 */
public class Monster implements Serializable, Comparable<Monster>, Cloneable {

    private static final long serialVersionUID = 1L;

    private static final Random RNG = new Random();

    private final String name;
    private int level;
    private int maxHp;
    private int currentHp;
    private int attack;
    private int defense;
    private int experience; // XP stored on the monster (can be used when defeated)

    /**
     * Create a Monster with basic stats.
     *
     * @param name      monster name (non-null)
     * @param level     starting level (>= 1)
     * @param maxHp     maximum HP (> 0)
     * @param attack    attack stat (>= 0)
     * @param defense   defense stat (>= 0)
     * @param experience experience points carried (>= 0)
     */
    public Monster(String name, int level, int maxHp, int attack, int defense, int experience) {
        if (name == null) throw new IllegalArgumentException("name cannot be null");
        this.name = name;
        this.level = Math.max(1, level);
        this.maxHp = Math.max(1, maxHp);
        this.currentHp = this.maxHp;
        this.attack = Math.max(0, attack);
        this.defense = Math.max(0, defense);
        this.experience = Math.max(0, experience);
    }

    /**
     * Convenience constructor for a low-level monster.
     *
     * @param name name of monster
     */
    public Monster(String name) {
        this(name, 1, 20, 5, 2, 0);
    }

    /* ----------------------
     * Basic getters/setters
     * ---------------------- */

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getExperience() {
        return experience;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = Math.max(1, maxHp);
        if (currentHp > this.maxHp) currentHp = this.maxHp;
    }

    public void setAttack(int attack) {
        this.attack = Math.max(0, attack);
    }

    public void setDefense(int defense) {
        this.defense = Math.max(0, defense);
    }

    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }

    /* ----------------------
     * Core combat methods
     * ---------------------- */

    /**
     * Executes an attack from this monster against the target.
     * Damage is calculated as:
     *   baseDamage = attack + levelModifier + randomVariance
     *   netDamage  = baseDamage - target.defense (minimum 0)
     *
     * @param target the Monster being attacked (must not be null)
     * @return the actual damage dealt (0 or positive)
     */
    public int attack(Monster target) {
        if (target == null) throw new IllegalArgumentException("target cannot be null");
        if (!this.isAlive()) return 0;
        if (!target.isAlive()) return 0;

        int levelModifier = this.level / 2; // small bonus from level
        int variance = RNG.nextInt(Math.max(1, this.attack + 1)); // 0 .. attack
        int baseDamage = this.attack + levelModifier + variance;
        int netDamage = baseDamage - target.defense;
        if (netDamage < 0) netDamage = 0;

        target.takeDamage(netDamage);
        return netDamage;
    }

    /**
     * Reduces this monster's HP by the damage amount.
     * HP will not drop below 0.
     *
     * @param damage non-negative damage
     */
    public void takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("damage cannot be negative");
        currentHp -= damage;
        if (currentHp < 0) currentHp = 0;
    }

    /**
     * Heals the monster by the specified amount, up to maxHp.
     *
     * @param amount amount to heal (non-negative)
     * @return actual amount healed
     */
    public int heal(int amount) {
        if (amount < 0) throw new IllegalArgumentException("heal amount cannot be negative");
        if (!isAlive()) return 0;
        int before = currentHp;
        currentHp = Math.min(maxHp, currentHp + amount);
        return currentHp - before;
    }

    /**
     * Fully restore HP to max.
     */
    public void fullHeal() {
        if (maxHp <= 0) return;
        currentHp = maxHp;
    }

    /**
     * True if current HP > 0.
     *
     * @return whether monster is alive
     */
    public boolean isAlive() {
        return currentHp > 0;
    }

    /* ----------------------
     * Leveling / progression
     * ---------------------- */

    /**
     * Add experience to the monster. Returns true if level-up occurred.
     * This uses a simple leveling curve: required XP = 100 * level
     *
     * When leveling up: level increases by 1 and stats improve slightly.
     *
     * @param xp amount of experience to add (>= 0)
     * @return true if one or more level-ups occurred
     */
    public boolean addExperience(int xp) {
        if (xp < 0) throw new IllegalArgumentException("xp cannot be negative");
        boolean leveled = false;
        experience += xp;

        // simple loop: allow multiple level-ups if XP big enough
        while (experience >= xpNeededForNextLevel()) {
            experience -= xpNeededForNextLevel();
            levelUp();
            leveled = true;
        }
        return leveled;
    }

    private int xpNeededForNextLevel() {
        return 100 * level;
    }

    private void levelUp() {
        level++;
        // small stat increases on level-up
        maxHp += 5 + RNG.nextInt(4); // +5..+8
        attack += 1 + RNG.nextInt(2); // +1..+2
        defense += RNG.nextInt(2);    // +0..+1
        // heal a bit on level up
        currentHp = Math.min(maxHp, currentHp + (maxHp / 4));
    }

    /* ----------------------
     * Utility
     * ---------------------- */

    @Override
    public String toString() {
        return String.format("%s (Lvl %d) HP: %d/%d ATK: %d DEF: %d XP: %d",
                name, level, currentHp, maxHp, attack, defense, experience);
    }

    @Override
    public int compareTo(Monster other) {
        if (other == null) return 1;
        int cmp = Integer.compare(this.level, other.level);
        if (cmp != 0) return cmp;
        return this.name.compareTo(other.name);
    }

    @Override
    public Monster clone() {
        try {
            Monster copy = (Monster) super.clone();
            // primitive fields are fine; RNG is static so no need to clone
            return copy;
        } catch (CloneNotSupportedException e) {
            // shouldn't happen since we implement Cloneable
            throw new AssertionError(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Monster)) return false;
        Monster monster = (Monster) o;
        return level == monster.level &&
                maxHp == monster.maxHp &&
                currentHp == monster.currentHp &&
                attack == monster.attack &&
                defense == monster.defense &&
                experience == monster.experience &&
                name.equals(monster.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, maxHp, currentHp, attack, defense, experience);
    }

    /* ----------------------
     * Example static factory methods
     * ---------------------- */

    /**
     * Create a simple Goblin preset.
     */
    public static Monster goblin() {
        return new Monster("Goblin", 1, 16, 4, 1, 10);
    }

    /**
     * Create a simple Orc preset.
     */
    public static Monster orc() {
        return new Monster("Orc", 3, 40, 9, 4, 40);
    }

    /**
     * Create a boss preset.
     */
    public static Monster ogreBoss() {
        return new Monster("Ogre Boss", 7, 120, 18, 10, 250);
    }

    /* ----------------------
     * Small demo main (optional)
     * ---------------------- */

    /**
     * Quick demonstration of Monster combat. Remove or comment out in production.
     */
    public static void main(String[] args) {
        Monster a = new Monster("Fenrir", 4, 55, 12, 6, 0);
        Monster b = Monster.orc();

        System.out.println("Starting battle:");
        System.out.println(a);
        System.out.println(b);
        System.out.println();

        int round = 1;
        while (a.isAlive() && b.isAlive() && round <= 20) {
            System.out.printf("Round %d: %s attacks %s -> damage %d%n",
                    round, a.getName(), b.getName(), a.attack(b));
            if (!b.isAlive()) {
                System.out.println(b.getName() + " was defeated!");
                a.addExperience(b.getExperience());
                break;
            }

            System.out.printf("Round %d: %s attacks %s -> damage %d%n",
                    round, b.getName(), a.getName(), b.attack(a));
            if (!a.isAlive()) {
                System.out.println(a.getName() + " was defeated!");
                break;
            }
            round++;
        }

        System.out.println();
        System.out.println("Battle ended:");
        System.out.println(a);
        System.out.println(b);
    }
}
