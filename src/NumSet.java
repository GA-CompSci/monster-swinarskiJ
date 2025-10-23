import java.util.ArrayList;


public class NumSet {

    /**
     * @param args the command line arguments
     */
    public static  void main(String[] args) {
        /*
        ROUND 1 tests
        */
        
        // Create a random int array of a given length, low and high end of range
        int[] randArray = randArray(15, 0, 100);
        
        // Create a random Integer ArrayList of given length, low and high range
        ArrayList<Integer> randArrL = randArrL(8, 5, 50);
        
        // How many similar elements are in a given array and ArrayList
        System.out.print("There are this many similar elements: ");
        System.out.println(compareNums(randArray, randArrL));
        
        // printPretty takes an int array and prints it out nicely
        printPretty(randArray);
        // printPretty takes an Integer ArrayList and prints it out nicely
        printPretty(randArrL);
        
        /*
        ROUND 2 tests
        */
        
        // shuffle randomizes an int array (then calls printPretty)
        shuffle(randArray);
        
        // shuffle randomizes an Integer ArrayList (then calls printPretty)
        shuffle(randArrL);
        
        // divide all numbers by two
        divByTwo(randArray);
        divByTwo(randArrL);
        
        //sumArray
        sumArray(randArray);
        sumArray(randArrL);
        
    }
    /*
    ROUND 1 code
    */
    
    // TODO: randArray
    public static int[] randArray(int length, int low, int high){
        int[] result = new int[length];
        
        for(int i = 0; i < result.length; i++){
            int rand = (int)(Math.random()*(high - low + 1))+low;
            result[i] = rand;
        }

        return result;
    }
    
    // TODO: randArrL
    public static ArrayList<Integer> randArrL(int length, int low, int high){
        ArrayList<Integer> result = new ArrayList<>();
        
        for(int i = 0; i < length; i++){
            int rand = (int)(Math.random()*(high - low + 1))+low;
            result.add(rand);
        }
        return result;
    }
    
    // TODO: compareNums
    public static int compareNums(int[] arr, ArrayList<Integer> arl){
        int count = 0;

        for(int num1 : arr){
            if (arl.contains(num1))count++;
        }

        return count;

    }
    
    // TODO: prettyPretty (overloaded)
    public static void printPretty(int[]arr){
        // traverse / print (neat and organized)
        for(int num : arr){
        System.out.print(num + " ");
        }
        System.out.println(arr);

    }

    public static void printPretty(ArrayList<Integer> arr){
        // traverse / print (neat and organized)
        for(int num : arr){
        System.out.print(num + " ");
        }
        System.out.println(arr);
        
    }
    /*
    ROUND 2 code
    */
    
    // TODO: shuffle array
    public static void shuffle(int[] arr){
        //traversal w for loop
            //pick rand location (0 , arr.length)
            //copy arr[random] to temp
            //move arr[i] to arr[random]
            //move temp to arr[i]

        for (int i = 0; i < arr.length; i++) {
            int randInx = (int)(Math.random() * arr.length);
            // 3 PART SWAP
            int temp = arr[randInx];
            arr[randInx] = arr[i];
            arr[i] = temp;
        }
        printPretty(arr);
    }
    
    // TODO: shuffle ArrayList
    public static void shuffle(ArrayList<Integer> arl) {
        for (int i = 0; i < arl.size(); i++) {
            int randInx = (int)(Math.random() * arl.size());
            // 3 PART SWAP
            int temp = arl.get(randInx);
            arl.set(randInx, arl.get(i));
            arl.set(i, temp);
        }
        printPretty(arl);
    }

    
    // TODO: divByTwo (overloaded)
    public static void divByTwo(int[] arr) {
    for (int i = 0; i < arr.length; i++) arr[i] /= 2;
    
    printPretty(arr);
    }

    public static void divByTwo(ArrayList<Integer> arl) {
    for (int i = 0; i < arl.size(); i++) {
        arl.set(i, arl.get(i) / 2);
    }
    printPretty(arl);
    }

    
    
    // TODO: sumArray (overloaded)
    public static int sumArray(int[] arr) {
    int sum = 0;
    for (int num : arr) sum += num;
    return sum;
    }

    public static int sumArray(ArrayList<Integer> arl) {
    int sum = 0;
    for (int num : arl) sum += num;
    return sum;
    }

}
