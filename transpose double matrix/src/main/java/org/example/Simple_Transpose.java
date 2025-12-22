package org.example;
import java.util.Arrays;

public class Simple_Transpose
{
    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] transposed = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    static void main() {


        double[][] matrix = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        double[][] result = transpose(matrix);

        for (double[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}
