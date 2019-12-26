package com.baeldung.matrices.apache;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

class RealMatrixUnitTest {

    @Test
    void givenTwoMatrices_whenMultiply_thenMultiplicatedMatrix() {
        RealMatrix firstMatrix = new Array2DRowRealMatrix(
          new double[][] {
            new double[] {1d, 5d},
            new double[] {2d, 3d},
            new double[] {1d ,7d}
          }
        );

        RealMatrix secondMatrix = new Array2DRowRealMatrix(
          new double[][] {
            new double[] {1d, 2d, 3d, 7d},
            new double[] {5d, 2d, 8d, 1d}
          }
        );

        RealMatrix expected = new Array2DRowRealMatrix(
          new double[][] {
            new double[] {26d, 12d, 43d, 12d},
            new double[] {17d, 10d, 30d, 17d},
            new double[] {36d, 16d, 59d, 14d}
          }
        );

        RealMatrix actual = firstMatrix.multiply(secondMatrix);

        assertThat(actual).isEqualTo(expected);
    }
}
