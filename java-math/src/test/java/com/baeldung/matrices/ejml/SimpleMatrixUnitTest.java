package com.baeldung.matrices.ejml;

import org.ejml.simple.SimpleMatrix;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleMatrixUnitTest {

    @Test
    void givenTwoMatrices_whenMultiply_thenMultiplicatedMatrix() {
        SimpleMatrix firstMatrix = new SimpleMatrix(
          new double[][] {
            new double[] {1d, 5d},
            new double[] {2d, 3d},
            new double[] {1d ,7d}
          }
        );

        SimpleMatrix secondMatrix = new SimpleMatrix(
          new double[][] {
            new double[] {1d, 2d, 3d, 7d},
            new double[] {5d, 2d, 8d, 1d}
          }
        );

        SimpleMatrix expected = new SimpleMatrix(
          new double[][] {
            new double[] {26d, 12d, 43d, 12d},
            new double[] {17d, 10d, 30d, 17d},
            new double[] {36d, 16d, 59d, 14d}
          }
        );

        SimpleMatrix actual = firstMatrix.mult(secondMatrix);

        assertThat(actual).matches(m -> m.isIdentical(expected, 0d));
    }

}