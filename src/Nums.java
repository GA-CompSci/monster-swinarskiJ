import java.util.ArrayList;
import java.util.Scanner;

public class Nums {
    public static void main(String[] args) {
        //ArrayLists are upgraded arrays with extra tools
        ArrayList<String> names = new ArrayList();

        Scanner s = new Scanner(System.in);
        System.out.println("Enter name: ");
        String input = s.nextLine();

        while(!input.equals("")){
            names.add(input);
            System.out.println("Name added. Enter next name (Enter nothing if finished): ");
            input = s.nextLine();
        }

        while(!input.equalsIgnoreCase("quit") || names.size() == 0){
            int r = (int)(Math.random()*names.size());
            System.out.println("Chosen name: "+names.get(r));
            names.add(input);
            names.remove(r);
            System.out.println("Enter next name (Type [QUIT] to quit): ");
            input = s.nextLine();
        }
        
    }
}
