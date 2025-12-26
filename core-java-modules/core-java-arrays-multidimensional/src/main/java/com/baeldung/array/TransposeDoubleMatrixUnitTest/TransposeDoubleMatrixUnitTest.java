package com.baeldung.array.TransposeDoubleMatrixUnitTest;
import java.util.stream.IntStream;
import java.util.stream.Stream;

    public class TransposeDoubleMatrix {

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

        public static void transposeInPlace(double[][] matrix) {
            int n = matrix.length;

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
        }

        public static double[][] transposeStream(double[][] matrix) {
            return IntStream.range(0, matrix[0].length)
                .mapToObj(col -> Stream.of(matrix)
                    .mapToDouble(row -> row[col])
                    .toArray()
                )
                .toArray(double[][]::new);
        }
    }


