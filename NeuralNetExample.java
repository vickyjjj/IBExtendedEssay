import Jama.Matrix; //Java Matrix library from http://math.nist.gov/javanumerics/jama/
import java.util.*;
import java.lang.*;

/**
 * @Victoria Juan
 * @September 2016
 * @Created for IB Extended Essay: To what extent do neural networks facilitate speech-based mobile 
 * intelligent assistants better than rule based programming?
 */
public class NeuralNetExample
{
    //Sigmoid function. Nested for loops iterate through each matrix value. If passed true, use sigmoid 
    //function; if false, use derivative in which the input is already the sigmoid output.
    public static Matrix sigmoidFunc(Matrix mat, boolean bool) 
    {
        double[][] arr = mat.getArrayCopy();
        for (int j = 0; j < arr.length; j++)
        {
            for (int k = 0; k < arr[j].length; k++)
            {
                if (bool)
                     arr[j][k] = 1.0 / (1.0 + Math.pow(Math.E, -1.0 * arr[j][k]));
                else
                     arr[j][k] = arr[j][k] * (1.0 - arr[j][k]);
            }
        }
        Matrix finalMat = new Matrix(arr);
        return finalMat;
    }
    
    //Random matrix function. Generates a random matrix of values between 0 and 1 for node connection
    //weights. Seeded in multiples of 3 for consistency between separate cases.
    public static Matrix randomMatrix(int row, int col)
    {
        double[][] arr = new double[row][col];
        Random rando = new Random(3);
        for (int j = 0; j < row; j++)
        {
            for (int k = 0; k < col; k++)
            {
                arr[j][k] = rando.nextDouble();
            }
        }
        Matrix finalMat = new Matrix(arr);
        return finalMat;
    }
    
    public static void main(String[] args)
    {
        //increases learning speed of backpropogation
        double learningRate = 1.2; 
        //inputArray used to test and set neural network
        double[][] inputArray = {{0, 0, 0},
                                {0, 1, 0},
                                {1, 0, 0},
                                {1, 1, 0}};
        //outputArray used to store results for neural network learning
        double[][] outputArray = {{0},
                                  {1},
                                  {1},
                                  {0}};
        Matrix input = new Matrix(inputArray);
        Matrix output = new Matrix(outputArray);
        
        //matrices with weights between initial and hidden layer/hidden and output layer
        Matrix weights0 = randomMatrix(3, 4);
        Matrix weights1 = randomMatrix(4, 1);
        System.out.println("BEGIN:");
        final long startTime = System.currentTimeMillis(); //begins learning timer
        for (int i = 0; i < 100000; i++)
        {
            //begin forward propogation
            Matrix nodes0 = input;
            //hidden layer values: sigmoid function of dot product of input values and connection weights
            Matrix nodes1 = sigmoidFunc(nodes0.times(weights0), true); 
            //final output values: sigmoid function of dot product hidden values and connection weights
            Matrix nodes2 = sigmoidFunc(nodes1.times(weights1), true);
            
            if (i % 10000 == 0)
            {
                System.out.println("WEIGHTS 0:");
                weights0.print(7, 5);
                System.out.println("WEIGHTS 1:");
                weights1.print(7, 5);
            }
            
            //final error: difference between intended output and actual results
            Matrix endingError = output.minus(nodes2);
            //necessary change to end weights: scalar product of error and sigmoid derivatives of final results
            Matrix endDeltaChange = endingError.arrayTimes(sigmoidFunc(nodes2, false));
            
            //hidden layer error: dot product of necessary change to end weights and ending weights
            Matrix midError = endDeltaChange.times(weights1.transpose());
            //necessary change to mid weights: scalar product of hidden layer error and sigmoid derivatives of hidden results
            Matrix midDeltaChange = midError.arrayTimes(sigmoidFunc(nodes1, false));
            
            //make changes to weights by adding dot product of nores and necessary change
            weights1.plusEquals(nodes1.transpose().times(endDeltaChange));
            weights0.plusEquals(nodes0.transpose().times(midDeltaChange));
            
            //print ending node results every 10,000 iterations
            if (i % 10000 == 0)
            {
                System.out.println("FINAL:");
                nodes2.print(7, 5);
            }
        }
        final long endTime = System.currentTimeMillis(); //end learning timer
        System.out.println("Total learning execution time: " + (endTime - startTime) );
        
        //test neural network with new inputs
        double[][] testInputArray = {{0, 0, 1},
                                    {0, 1, 1},
                                    {1, 0, 1},
                                    {1, 1, 1}};
        Matrix inputTest = new Matrix(testInputArray);
        
        final long startTime1 = System.currentTimeMillis();
        //calulate and print results for test values 
        Matrix testResults = sigmoidFunc(sigmoidFunc(inputTest.times(weights0), true).times(weights1), true);
        testResults.print(7, 5);
        final long endTime1 = System.currentTimeMillis();
        
        System.out.println("Total testing execution time: " + (endTime1 - startTime1) );
    }
}
