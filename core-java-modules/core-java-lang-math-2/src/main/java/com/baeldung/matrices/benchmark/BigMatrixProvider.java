package com.baeldung.matrices.benchmark;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.BenchmarkParams;

import java.util.Random;
import java.util.stream.DoubleStream;

@State(Scope.Benchmark)
public class BigMatrixProvider {
    @Param({})
    private int matrixSize;
    private double[][] firstMatrix;
    private double[][] secondMatrix;

    public BigMatrixProvider() {}

    @Setup
    public void setup(BenchmarkParams parameters) {
        firstMatrix = createMatrix(matrixSize);
        secondMatrix = createMatrix(matrixSize);
    }

    private double[][] createMatrix(int matrixSize) {
        Random random = new Random();

        double[][] result = new double[matrixSize][matrixSize];
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = random.nextDouble();
            }
        }
        return result;
    }

    public double[][] getFirstMatrix() {
        return firstMatrix;
    }

    public double[][] getSecondMatrix() {
        return secondMatrix;
    }
}