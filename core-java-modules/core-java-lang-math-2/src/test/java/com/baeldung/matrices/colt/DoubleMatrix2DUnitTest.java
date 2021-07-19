package com.baeldung.matrices.colt;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

class DoubleMatrix2DUnitTest {

    @Test
    void givenTwoMatrices_whenMultiply_thenMultiplicatedMatrix() {
        DoubleFactory2D doubleFactory2D = DoubleFactory2D.dense;

        DoubleMatrix2D firstMatrix = doubleFactory2D.make(
          new double[][] {
            new double[] {1d, 5d},
            new double[] {2d, 3d},
            new double[] {1d ,7d}
          }
        );

        DoubleMatrix2D secondMatrix = doubleFactory2D.make(
          new double[][] {
            new double[] {1d, 2d, 3d, 7d},
            new double[] {5d, 2d, 8d, 1d}
          }
        );

        DoubleMatrix2D expected = doubleFactory2D.make(
          new double[][] {
            new double[] {26d, 12d, 43d, 12d},
            new double[] {17d, 10d, 30d, 17d},
            new double[] {36d, 16d, 59d, 14d}
          }
        );

        Algebra algebra = new Algebra();
        DoubleMatrix2D actual = algebra.mult(firstMatrix, secondMatrix);

        assertThat(actual).isEqualTo(expected);
    }

}