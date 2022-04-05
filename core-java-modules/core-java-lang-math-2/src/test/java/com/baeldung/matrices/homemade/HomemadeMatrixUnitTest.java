package com.baeldung.matrices.homemade;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

class HomemadeMatrixUnitTest {

    @Test
    void givenTwoMatrices_whenMultiply_thenMultiplicatedMatrix() {
        double[][] firstMatrix = {
          new double[]{1d, 5d},
          new double[]{2d, 3d},
          new double[]{1d, 7d}
        };

        double[][] secondMatrix = {
          new double[]{1d, 2d, 3d, 7d},
          new double[]{5d, 2d, 8d, 1d}
        };

        double[][] expected = {
          new double[]{26d, 12d, 43d, 12d},
          new double[]{17d, 10d, 30d, 17d},
          new double[]{36d, 16d, 59d, 14d}
        };

        double[][] actual = multiplyMatrices(firstMatrix, secondMatrix);

        assertThat(actual).isEqualTo(expected);
    }

    private double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }

    private double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

}