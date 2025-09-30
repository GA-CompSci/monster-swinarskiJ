import java.util.Scanner;

public class App {

    private static int health = 100;
    private static int speed = 10;
    private static int shield = 50;
    private static int damage = 50;
    private static int heal = 50;

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
            //low heal and shield
            heal = 5;
        }
        else if(choice == 2){
            
        }
        else if(choice == 3){
            
        }
        else{
            
        }
    

        //MONSTER STATUS
        reportMonsters();

        //PICK MONSTERS 
        Monster currentMonster = getNextMonster();
        

         // GAME LOOP
        while(monsterCount(0)>0)
        {
        // Who is first?


        //Options
            System.out.println("---OPTIONS---");
            System.out.println("1) Attack");
            System.out.println("2) Defend");
            System.out.println("3) Heal");
            System.out.println("4) Pass");
            System.out.print("Choice: ");
            int choice = input.nextInt();  //TODO: error handle on non-int input

            //choice

            if(choice == 1){

            }
            else if(choice == 2){
                
            }
            else if(choice == 3){
                
            }
            else{
                
            }

            if (currentMonster.health() <=0){
                System.out.println("\n !!Monster has been slayed !!\n");
                currentMonster = getNextMonster();
            }

        }

    }

    public static void reportMonsters(){
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
