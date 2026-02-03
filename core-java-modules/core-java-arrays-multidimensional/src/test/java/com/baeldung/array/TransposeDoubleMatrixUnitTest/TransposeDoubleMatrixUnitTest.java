package com.baeldung.array.TransposeDoubleMatrixUnitTest;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.array.TransposeDoubleMatrix.TransposeDoubleMatrix;

class TransposeDoubleMatrixUnitTest {

    @Test
    void givenMatrix_whenTranspose_thenReturnsTransposedMatrix() {
        double[][] input = {
            {1.0, 2.0, 3.0},
            {4.0, 5.0, 6.0}
        };
        double[][] expected = {
            {1.0, 4.0},
            {2.0, 5.0},
            {3.0, 6.0}
        };

        double[][] result = TransposeDoubleMatrix.transpose(input);

        assertThat(result).isEqualTo(expected);
    }


    @Test
    void givenSquareMatrix_whenTransposeInPlace_thenTransposesOriginalMatrix() {
        double[][] matrix = {
            {1.0, 2.0},
            {3.0, 4.0}
        };
        double[][] expected = {
            {1.0, 3.0},
            {2.0, 4.0}
        };

        TransposeDoubleMatrix.transposeInPlace(matrix);

        assertThat(matrix).isEqualTo(expected);
    }

    @Test
    void givenMatrix_whenTransposeStream_thenReturnsTransposedArray() {
        double[][] input = {
            {1.0, 2.0},
            {3.0, 4.0},
            {5.0, 6.0}
        };
        double[][] expected = {
            {1.0, 3.0, 5.0},
            {2.0, 4.0, 6.0}
        };

        double[][] result = TransposeDoubleMatrix.transposeStream(input);

        assertThat(result).isEqualTo(expected);
    }
}
