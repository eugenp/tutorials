package com.baeldung.matrices.la4j;

import org.junit.jupiter.api.Test;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.openjdk.jmh.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

class Basic2DMatrixUnitTest {

    @Test
    void givenTwoMatrices_whenMultiply_thenMultiplicatedMatrix() {
        Matrix firstMatrix = new Basic2DMatrix(
          new double[][]{
            new double[]{1d, 5d},
            new double[]{2d, 3d},
            new double[]{1d, 7d}
          }
        );

        Matrix secondMatrix = new Basic2DMatrix(
          new double[][]{
            new double[]{1d, 2d, 3d, 7d},
            new double[]{5d, 2d, 8d, 1d}
          }
        );

        Matrix expected = new Basic2DMatrix(
          new double[][]{
            new double[]{26d, 12d, 43d, 12d},
            new double[]{17d, 10d, 30d, 17d},
            new double[]{36d, 16d, 59d, 14d}
          }
        );

        Matrix actual = firstMatrix.multiply(secondMatrix);

        assertThat(actual).isEqualTo(expected);
    }

}