package org.example;

public class HungarianMethod {

    static double calculate(double[][] matrix){
        double minCost = 0;
        double[][] tempMatrix = matrix;
        for(int i = 0; i < tempMatrix.length; i++)
        {
            double minRowElement = findMinRowElement(tempMatrix[i]);
            for(int j = 0; j < tempMatrix[i].length; j++)
            {
                tempMatrix[i][j] -= minRowElement;
            }
        }
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
    private static double findMinColElement(double[] col){
        double minElement = col[0];
        return minElement;
    }



}
