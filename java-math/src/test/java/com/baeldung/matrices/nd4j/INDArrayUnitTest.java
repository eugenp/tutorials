package com.baeldung.matrices.nd4j;

import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.openjdk.jmh.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

class INDArrayUnitTest {

    @Test
    void givenTwoMatrices_whenMultiply_thenMultiplicatedMatrix() {
        INDArray firstMatrix = Nd4j.create(
          new double[][]{
            new double[]{1d, 5d},
            new double[]{2d, 3d},
            new double[]{1d, 7d}
          }
        );

        INDArray secondMatrix = Nd4j.create(
          new double[][] {
            new double[] {1d, 2d, 3d, 7d},
            new double[] {5d, 2d, 8d, 1d}
          }
        );

        INDArray expected = Nd4j.create(
          new double[][] {
            new double[] {26d, 12d, 43d, 12d},
            new double[] {17d, 10d, 30d, 17d},
            new double[] {36d, 16d, 59d, 14d}
          }
        );

        INDArray actual = firstMatrix.mmul(secondMatrix);

        assertThat(actual).isEqualTo(expected);
    }

}