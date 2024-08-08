package com.baeldung.array.sumofdiagonal;

import static com.baeldung.array.sumofdiagonal.DiagonalType.Main;
import static com.baeldung.array.sumofdiagonal.DiagonalType.Secondary;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

enum DiagonalType {
    Main((rowIdx, len) -> rowIdx),
    Secondary((rowIdx, len) -> (len - rowIdx - 1));

    public final IntBinaryOperator colIdxOp;

    DiagonalType(IntBinaryOperator colIdxOp) {
        this.colIdxOp = colIdxOp;
    }
}

public class SumOfDiagonalValuesIn2dArrayUnitTest {

    // @formatter:off
    private static final int[][] MATRIX = new int[][] {
        {  1,  2,  3,  4 },
        {  5,  6,  7,  8 },
        {  9, 10, 11, 12 },
        { 13, 14, 15, 100 }
    };
    // @formatter:on
    private static final int SUM_MAIN_DIAGONAL = 118; //1+6+11+100
    private static final int SUM_SECONDARY_DIAGONAL = 34; //4+7+10+13

    public int diagonalSumBySingleLoop(int[][] matrix, DiagonalType diagonalType) {
        int sum = 0;
        int n = matrix.length;
        for (int row = 0; row < n; row++) {
            int colIdx = diagonalType == Main ? row : n - row - 1;
            sum += matrix[row][colIdx];
        }
        return sum;
    }

    @Test
    void whenUsingSingleLoop_thenCorrect() {
        assertEquals(SUM_MAIN_DIAGONAL, diagonalSumBySingleLoop(MATRIX, Main));
        assertEquals(SUM_SECONDARY_DIAGONAL, diagonalSumBySingleLoop(MATRIX, Secondary));
    }

    public int diagonalSumFunctional(int[][] matrix, DiagonalType diagonalType) {
        int sum = 0;
        int n = matrix.length;
        for (int row = 0; row < n; row++) {
            sum += matrix[row][diagonalType.colIdxOp.applyAsInt(row, n)];
        }
        return sum;
    }

    @Test
    void whenUsingEnumWithLambda_thenCorrect() {
        assertEquals(SUM_MAIN_DIAGONAL, diagonalSumFunctional(MATRIX, Main));
        assertEquals(SUM_SECONDARY_DIAGONAL, diagonalSumFunctional(MATRIX, Secondary));
    }

    public int diagonalSumFunctionalByStream(int[][] matrix, DiagonalType diagonalType) {
        int n = matrix.length;
        return IntStream.range(0, n)
            .map(i -> MATRIX[i][diagonalType.colIdxOp.applyAsInt(i, n)])
            .sum();
    }

    @Test
    void whenUsingEnumWithStream_thenCorrect() {
        assertEquals(SUM_MAIN_DIAGONAL, diagonalSumFunctionalByStream(MATRIX, Main));
        assertEquals(SUM_SECONDARY_DIAGONAL, diagonalSumFunctionalByStream(MATRIX, Secondary));
    }
}