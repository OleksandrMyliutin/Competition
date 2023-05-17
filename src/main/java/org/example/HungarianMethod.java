package org.example;

import java.util.ArrayList;
import java.util.List;

public class HungarianMethod {

    static double calculate(double[][] matrix){
        double minCost = 0;
        double[][] startMatrix = copy2DArray(matrix);
        double[][] tempMatrix = copy2DArray(matrix);
        for(int i = 0; i < tempMatrix.length; i++)
        {
            double minRowElement = findMinRowElement(tempMatrix[i]);
            for(int j = 0; j < tempMatrix[i].length; j++)
            {
                tempMatrix[i][j] -= minRowElement;
            }
        }
        findAndDecreaseMinColElement(tempMatrix);
        ArrayList<ElementCoordinates> coordinates = findFinalZerosIndexes(tempMatrix);
        minCost = sumZeroElements(startMatrix, coordinates);
        return minCost;
    }

    public static double findMinRowElement(double[] row){
        double minElement = row[0];
        for (int i = 0; i < row.length; i++) {
            if (minElement > row[i]){
                minElement = row[i];
            }
        }
        return minElement;
    }
    private static void findAndDecreaseMinColElement(double[][] matrix){
        for(int j = 0; j < matrix[0].length; j++)
        {
            double minColElement = matrix[0][j];
            for(int i = 1; i < matrix.length; i++)
            {
                if(matrix[i][j] < minColElement) minColElement = matrix[i][j];
            }

            for(int i = 0; i < matrix.length; i++)
            {
                matrix[i][j] -= minColElement;
            }
        }
    }
    private static ArrayList<ElementCoordinates> findFinalZerosIndexes(double[][] matrix)
    {
        var indexes = new ArrayList<ElementCoordinates>();
        while(indexes.size() < matrix.length) {
            for (int i = 0; i < matrix.length; i++) {
                int countZeroes = 0;
                int zeroIndexI = -1;
                int zeroIndexJ = -1;

                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] == 0d) {
                        countZeroes++;
                        zeroIndexI = i;
                        zeroIndexJ = j;
                    }
                }
                if (countZeroes == 1 && indexes.indexOf(new ElementCoordinates(zeroIndexI, zeroIndexJ)) == -1) {
                    indexes.add(new ElementCoordinates(zeroIndexI, zeroIndexJ));
                    for (int k = 0; k < matrix.length; k++) {
                        if (matrix[k][zeroIndexJ] == 0d && k != zeroIndexI) {
                            matrix[k][zeroIndexJ] = Integer.MAX_VALUE;
                        }
                    }
                }
            }
        }
        return indexes;
    }
    private static double sumZeroElements(double[][] matrix, ArrayList<ElementCoordinates> zeroCoordinates)
    {
        double sum = 0;
        for (var itemCoordinates: zeroCoordinates) {
            sum += matrix[itemCoordinates.getI()][itemCoordinates.getJ()];
        }
        return sum;
    }
    private static double[][] copy2DArray(double[][] source)
    {
        double[][] destination = new double[source.length][];

        for (int i = 0; i < source.length; ++i) {

            // allocating space for each row of destination array
            destination[i] = new double[source[i].length];
            System.arraycopy(source[i], 0, destination[i], 0, destination[i].length);
        }
        return destination;
    }
}
