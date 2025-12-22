package org.example;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.Arrays;

public class Streams_Transpose {

    public static double[][] transposeStream(final double[][] matrix) {
        return IntStream.range(0, matrix[0].length)
                .mapToObj(col -> Stream.of(matrix)
                        .mapToDouble(row -> row[col])
                        .toArray()
                )
                        .toArray(double[][]::new);
    }

    static void main() {

        double[][] matrix = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };

        double[][] transposed = transposeStream(matrix);

        for (double[] row : transposed) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

}
