package org.example;
import java.util.Arrays;
public class Transpose_InPlace
{

    public static void transposeInPlace(double[][] matrix) {
        int n = matrix.length;

        // Swap elements across the diagonal
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

static void main()
    {
        double[][] matrixSquare = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        transposeInPlace(matrixSquare);

        for (double[] row : matrixSquare) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

    }
}
