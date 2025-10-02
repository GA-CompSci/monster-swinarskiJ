public class Weapon {
    
    //instance vars
    private int damage;
    private String name;


    //contrsuctor
    public Weapon(int dmg, String nm){
        this.damage = dmg;
        this.name = nm;
    }


    //accessor
    public int getDamage(){
        return this.damage;
    }

    public String getName(){
        return this.name;
    }

}
