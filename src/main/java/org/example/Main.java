package org.example;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        double[][] testMatrix = {
                {6, 3, 8, 5},
                {9, 7, 2, 4},
                {5, 8, 3, 7}};
        for (int i = 0; i < testMatrix.length; i++)
        {
            for(int j = 0; j < testMatrix[i].length; j++)
            {
                System.out.print(testMatrix[i][j] + "\t");
            }
            System.out.print('\n');
        }
        System.out.println(HungarianMethod.findMinRowElement(testMatrix[0]));
    }
}