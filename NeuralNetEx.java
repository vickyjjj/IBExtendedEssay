import Jama.Matrix;
import java.util.*;
import java.lang.*;

/**
 * Example of a neural network.
 * 
 * @Victoria Juan
 * @September 2016
 * @Created for IB Extended Essay: To what extent do neural networks facilitate speech-based mobile 
 * intelligent assistants better than rule based programming?
 */
public class NeuralNetEx
{
    public static Matrix sigmoidFunc(Matrix mat, boolean bool)
    {
        double[][] arr = mat.getArray();
        for (int j = 0; j < arr.length; j++)
        {
            for (int k = 0; k < arr[j].length; k++)
            {
                if (bool)
                     arr[j][k] = sigmoid(arr[j][k]);
                else
                     arr[j][k] = arr[j][k] * (1.0 - arr[j][k]);
            }
        }
        Matrix finalMat = new Matrix(arr);
        return finalMat;
    }
    
    public static double sigmoid(double num)
    {
        return 1.0 / (1.0 + Math.pow(Math.E, -1.0 * num));
    }
    
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
    
    public static Matrix divideAvg(Matrix a, Matrix b, boolean bool)
    {
        double[][] arrDivided = a.getArray();
        double[][] arrResults = b.getArray();
        if (bool)
            arrResults = b.getArray();
        else
        {
            arrResults = 
        }
        for (int j = 0; j < arrResults.length; j++)
        {
            for (int k = 0; k < arrResults[j].length; j++)
            {
                arrResults[j][k] = arrDivided[j][0] / arrResults[j][k];
            }
        }
        
        double[][] arrFinal = new double[arrResults[0].length][1];
        for (int j = 0; j < arrResults[0].length; j++)
        {
            for (int k = 0; k < arrResults.length; j++)
            {
                arrFinal[j][0] += arrResults[k][j];
            }
            arrFinal[j][0] /= 4;
        }
        Matrix finalMat = new Matrix(arrFinal);
        return finalMat;
    }
    
    public static void main(String[] args)
    {
        double learningRate = 0.7;
        double[][] inputArray = {{0, 0, 1},
                                {0, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1}};
        double[][] outputArray = {{0},
                                  {1},
                                  {1},
                                  {0}};
        Matrix input = new Matrix(inputArray);
        Matrix output = new Matrix(outputArray);
        
        Matrix weights0 = randomMatrix(3, 4);
        Matrix weights1 = randomMatrix(4, 1);
        
        for (int i = 0; i < 1500000; i++)
        {
            Matrix nodes0 = input;
            Matrix results1 = nodes0.times(weights0);
            Matrix nodes1 = sigmoidFunc(results1, true);
            Matrix results2 = nodes1.times(weights1);
            Matrix nodes2 = sigmoidFunc(results2, true);

            Matrix endingError = output.minus(nodes2);
            Matrix endDeltaChange = endingError.arrayTimes(sigmoidFunc(results2, false)); //1
            Matrix endDeltaWeights = divideAvg(endDeltaChange, nodes1, true); //2
            
            //Matrix midError = endDeltaChange.times(weights1.transpose());
            Matrix midDeltaChange = divide(endDeltaChange, weights1.transpose(), false).arrayTimes(sigmoidFunc(results1, false)); //3
            Matrix midDeltaWeights = divideAvg(midDeltaChange, nodes0);
            
            weights1.plusEquals(endDeltaWeights);
            weights0.plusEquals(nodes0.transpose().times(midDeltaChange));
            
            if (i % 100000 == 0)
            {
                endingError.print(7, 5);
                System.out.println("!!");
            }
        }
        
        
    }
}
