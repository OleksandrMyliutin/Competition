package org.example;

import java.util.ArrayList;
import java.util.Comparator;

public class HungarianMethod {

    public static ArrayList<ElementCoordinates> calculateCosts(double[][] matrix, boolean isMax){
        if(matrix.length == 0) {
            throw new RuntimeException("Matrix is empty!");
        }
        double cost = 0;
        double[][] startMatrix = copy2DArray(matrix);
        double[][] tempMatrix = copy2DArray(matrix);
        decreaseElement(tempMatrix, isMax);
        ArrayList<ElementCoordinates> coordinates = findFinalZerosIndexes(tempMatrix);
        coordinates.sort((o1, o2) -> {
            if(o1.equals(o2)) return 0;
            if(o1.getI() > o2.getI()) return 1;
            return -1;
        });
        return coordinates;
    }

    private static void decreaseElement(double[][] tempMatrix, boolean isMax)
    {
        if(isMax) {
            for (int i = 0; i < tempMatrix.length; i++) {
                double maxRowElement = findMaxRowElement(tempMatrix[i]);
                for (int j = 0; j < tempMatrix[i].length; j++) {
                    tempMatrix[i][j] -= maxRowElement;
                }
            }
            for (int j = 0; j < tempMatrix[0].length; j++) {
                double maxColElement = findMaxColElement(tempMatrix, j);
                for (int i = 0; i < tempMatrix.length; i++) {
                    tempMatrix[i][j] -= maxColElement;
                }
            }
            return;
        }
        for (int i = 0; i < tempMatrix.length; i++) {
            double minRowElement = findMinRowElement(tempMatrix[i]);
            for (int j = 0; j < tempMatrix[i].length; j++) {
                tempMatrix[i][j] -= minRowElement;
            }
        }
        for (int j = 0; j < tempMatrix[0].length; j++) {
            double minColElement = findMinColElement(tempMatrix, j);
            for (int i = 0; i < tempMatrix.length; i++) {
                tempMatrix[i][j] -= minColElement;
            }
        }
    }
    private static double findMinRowElement(double[] row){
        double minElement = row[0];
        for (int i = 0; i < row.length; i++) {
            if (minElement > row[i]){
                minElement = row[i];
            }
        }
        return minElement;
    }
    private static double findMaxRowElement(double[] row){
        double maxElement = row[0];
        for (int i = 0; i < row.length; i++) {
            if (maxElement < row[i]){
                maxElement = row[i];
            }
        }
        return maxElement;
    }
    private static double findMinColElement(double[][] matrix, int j){
        double minColElement = matrix[0][j];
        for(int i = 1; i < matrix.length; i++)
        {
            if(matrix[i][j] < minColElement) minColElement = matrix[i][j];
        }
        return minColElement;
    }
    private static double findMaxColElement(double[][] matrix, int j){
        double maxColElement = matrix[0][j];
        for(int i = 1; i < matrix.length; i++)
        {
            if(matrix[i][j] > maxColElement) maxColElement = matrix[i][j];
        }
        return maxColElement;
    }
    private static ArrayList<ElementCoordinates> findFinalZerosIndexes(double[][] matrix)
    {
        var indexes = new ArrayList<ElementCoordinates>();
        var zeroes = new ArrayList<ElementCoordinates>();
        while(indexes.size() < matrix.length) {
            zeroes.clear();
            for (int i = 0; i < matrix.length; i++) {
                int countZeroes = 0;
                int zeroIndexI = -1;
                int zeroIndexJ = -1;
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] == 0d) {
                        zeroes.add(new ElementCoordinates(i, j));
                        countZeroes++;
                        zeroIndexI = i;
                        zeroIndexJ = j;
                    }
                }
                if (countZeroes == 1 && indexes.indexOf(new ElementCoordinates(zeroIndexI, zeroIndexJ)) == -1) {indexes.add(new ElementCoordinates(zeroIndexI, zeroIndexJ));
                    for (int k = 0; k < matrix.length; k++) {
                        if (matrix[k][zeroIndexJ] == 0d && k != zeroIndexI) {
                            matrix[k][zeroIndexJ] = Integer.MAX_VALUE;
                        }
                    }
                }
            }
            if(zeroes.size() < matrix.length)
            {
                throw new RuntimeException("Ця матриця не може мати оптимільні рішенн для кожного проекту!");
            }
        }
        return indexes;
    }
    public static double sumZeroElements(double[][] matrix, ArrayList<ElementCoordinates> cords)
    {
        double sum = 0;
        double[] desipher = desipher(matrix, cords);
        for (var itemCoordinates: desipher) {
            sum += itemCoordinates;
        }
        return sum;
    }
    private static double[] desipher(double[][] matrix, ArrayList<ElementCoordinates> cords)
    {
        double[] result = new double[cords.size()];
        for (int i = 0; i < cords.size(); i++) {
            int row = cords.get(i).getI();
            int col = cords.get(i).getJ();
            result[i] = matrix[row][col];
        }
        return result;
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