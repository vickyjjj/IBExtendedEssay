import java.util.*;
import java.lang.*;
/**
 * @Victoria Juan 
 * @September 2016
 * @Written for IB Extended Essay: To what extent do neural networks facilitate speech-based mobile 
 * intelligent assistants better than rule based programming?
 */
public class RuleBasedExample
{
    public static int finalState; //global value to track current state of program
    
    //rule monitor: if first element is 0, send to xorRules0, else first element is 1, so send to xorRules1
    public static void monitor(double[] arr)
    {
        if (arr[0] == 0)
            xorRules0(arr);
        else
            xorRules1(arr);
    }
    
    //xorRules0: decides state based on array's second value with given first value as 0
    public static void xorRules0(double[] arr)
    {
        if (arr[1] == 0)
            finalState = 0;
        else
            finalState = 1;
    }
    
    //xorRules1: decides state based on array's second value with given first value as 1
    public static void xorRules1(double[] arr)
    {
        if (arr[1] == 0)
            finalState = 1;
        else
            finalState = 0;
    }
    
    //method to print array
    public static void printArr(double[][] arr)
    {
        for (int i = 0; i < arr.length; i++)
            System.out.println(arr[i][0]);
    }
    
    public static void main(String[] args)
    {
        //test rules (no training required)
        double[][] inputArray = {{0, 0, 0},
                                {0, 1, 0},
                                {1, 0, 0},
                                {1, 1, 0}};
        double[][] outputArray = new double[4][1];
        
        final long startTime = System.currentTimeMillis(); //begin timer
        //for each set of input values in array, send to monitor to decide rules
        for (int i = 0; i < inputArray.length; i++)
        {
            monitor(inputArray[i]);
            outputArray[i][0] = finalState; 
        }
        
        printArr(outputArray);
        final long endTime = System.currentTimeMillis();
        
        System.out.println("Total testing execution time: " + (endTime - startTime) );
    }
}
