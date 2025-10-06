import java.util.Scanner;

public class App {


    //Maxed stats (set to default)
    private static int health = 100;
    private static int speed = 10;
    private static int shield = 50;
    private static int damage = 50;
    private static int heal = 50;

    //State Vars (automatically set to false)
    private static boolean isDefend = false;

    // CLASS VARIABLE
    private static Monster[] monsters;
    public static void main(String[] args) throws Exception {
        System.out.println("----- MONSTER BATTLE -----");
        Scanner input = new Scanner(System.in);
        System.out.print("How many monsters will you slay: ");
        int num = input.nextInt(); // TODO: handle errors if it's not a number
        monsters = new Monster[num];
                // build all the monsters
        for(int i = 0; i < monsters.length; i++){
            monsters[i] = new Monster(); // TODO: add some specials
        }

        // PICK BUILD
         System.out.println("---PICK YOUR BUILD---");
        System.out.println("1) Fighter");
        System.out.println("2) Tank");
        System.out.println("3) Healer");
        System.out.println("4) Ninja");
        System.out.print("Choice: ");
        int choice = input.nextInt();  //TODO: error handle on non-int input

        //build choice

        if(choice == 1){
            //heal and shield
            shield -= (int)(Math.random()* 45 + 1) + 5;
            heal -= (int)(Math.random()* 46) + 5;
    
        }
        else if(choice == 2){
            // speed and dmg
            speed -= (int)(Math.random()* 9) + 1;
            damage -= (int)(Math.random()* 26) + 5;
            
        }
        else if(choice == 3){
            //dmg and shield
            damage -= (int)(Math.random()* 26) + 5;
            shield -= (int)(Math.random()* 46) + 5;
        }
        else{
            //hp and heal
            health-= (int)(Math.random()* 21) + 5;
            heal-= (int)(Math.random()* 46) + 5;
        }
    

        //MONSTER STATUS
        reportMonsters();

        //PICK MONSTERS 
        Monster currentMonster = getNextMonster();
        

         // GAME LOOP
        while(monsterCount(0)>0){
            isDefend = false;

        //Options
            System.out.println("---OPTIONS---");
            System.out.println("1) Attack");
            System.out.println("2) Defend");
            System.out.println("3) Heal");
            System.out.println("4) Pass");
            System.out.print("Choice: ");
            choice = input.nextInt();  //TODO: error handle on non-int input

            //choice

            if(choice == 1){
                int dmg = (int)(Math.random()* damage + 1);
                if (dmg == damage) dmg = currentMonster.health();
                
                else if (dmg == 0) {
                    System.out.println("----FAIL----");
                    System.out.println("You managed to hit yourself instead of the enemy... how?");
                    health -= 10;
                }
                
                else currentMonster.takeDamage(dmg);
            }
            else if(choice == 2){
                isDefend = true;
                System.err.println("----SHIELD UP!----");
            }
            else if(choice == 3){
                int h = (int)(Math.random()+ heal + 1);
                health += h;
                System.out.println("----HEALED FOR " + h +" HP (Current Health: " + health +")----");
            }
            else{
                speed ++;
                System.out.println("----SPEED INCREASED (Current Speed: " + speed +")----");
            }

            if (currentMonster.health() <=0){
                System.out.println("\n !! Monster has been slayed !!\n");
                currentMonster = getNextMonster();
                reportMonsters();
                continue; // GO AGAIN AFTER MONSTER IS SLAYED
            }

            //MONSTER'S TURN
            int speedCheck = (int)(Math.random()* 100);
            if(speedCheck <= speed){ // BONUS TURN
                System.out.println("\n !! BONUS TURN !!\n");
                continue;
            }
            else{
                int incomingDamage = (int)(Math.random()* currentMonster.damage() + 1);
                if (isDefend){ 
                    incomingDamage -= shield;
                    if (incomingDamage < 0){
                        incomingDamage = 0;
                    }
                    System.out.println("----SHIELD HAS ABSORBED " + shield + " DAMAGE----");
                }
                health -= incomingDamage;
            }

            //DID I DIE?
            if (health <= 0){
                reportMonsters();
                System.out.println("----YOU WERE KILLED IN COMBAT----");
                System.out.println("\n\n-------GAME OVER-------\n\n");
                break;
            }


        }

    }

    public static void reportMonsters(){

        System.out.println("-PLAYER RPORT-");
        System.out.println("Health: " + health);
        System.out.println("Heal: " + heal);
        System.out.println("Speed: " + speed);
        System.out.println("Damage: " + damage);
        System.out.println("Shield: " + shield);
        


        System.out.println("-MONSTER RPORT-");

        for(int i=0; i<monsters.length; i++)
        {
            System.out.println("Monster #"+i);
            System.out.println("Health:" + monsters[i].health());
            System.out.println("Speed:" + monsters[i].speed());
            System.out.println("Damage:" + monsters[i].damage());
            if(!monsters[i].special().equals(""))
            {
                System.out.println("Special:" + monsters[i].special());
            }
            System.out.println();
        }
        
    }

    public static double percentComplete(){

        return 100 - monsterCount(0)/monsters.length*100;
    }
    
    /**
     * How many monsters have over the given health
     * @param health number to check
     * @return number of monsters above that health
     */
    public static int monsterCount(int health){
        int count = 0;
        // traverse the monsters array and find out how many have < 50 health
        for(Monster m : monsters){
            if(m.health() > health) count++;
        }
        return count;
    }

    public static Monster getNextMonster() {
        for(int i = 0; i < monsters.length; i++){
            if(monsters[i].health() > 0) return monsters[i];
        }
        return null;
    }






    
}
