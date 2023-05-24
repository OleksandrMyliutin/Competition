package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        double[][] testMatrix = {
                {2, 4, 1, 3, 3},
                {1, 5, 4, 1, 2},
                {3, 5, 2, 2, 4},
                {1, 4, 3, 1, 4},
                {3, 2, 5, 3, 5}
        };
        for (int i = 0; i < testMatrix.length; i++)
        {
            for(int j = 0; j < testMatrix[i].length; j++)
            {
                System.out.print(testMatrix[i][j] + "\t");
            }
            System.out.print('\n');
        }
//        System.out.println("Minimal cost of hungarian method: " + HungarianMethod.calculate(testMatrix));
    }
}