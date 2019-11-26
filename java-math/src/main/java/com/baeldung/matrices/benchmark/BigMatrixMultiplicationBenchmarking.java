package com.baeldung.matrices.benchmark;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import com.baeldung.matrices.HomemadeMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.ejml.simple.SimpleMatrix;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BigMatrixMultiplicationBenchmarking {
    private static final int DEFAULT_FORKS = 2;
    private static final int DEFAULT_WARMUP_ITERATIONS = 5;
    private static final int DEFAULT_MEASUREMENT_ITERATIONS = 10;

    public static void main(String[] args) throws Exception {
        Map<String, String> parameters = parseParameters(args);

        ChainedOptionsBuilder builder = new OptionsBuilder()
          .include(BigMatrixMultiplicationBenchmarking.class.getSimpleName())
          .mode(Mode.AverageTime)
          .forks(forks(parameters))
          .warmupIterations(warmupIterations(parameters))
          .measurementIterations(measurementIterations(parameters))
          .timeUnit(TimeUnit.SECONDS);

        parameters.forEach(builder::param);

        new Runner(builder.build()).run();
    }

    private static Map<String, String> parseParameters(String[] args) {
        return Arrays.stream(args)
          .map(arg -> arg.split("="))
          .collect(Collectors.toMap(
            arg -> arg[0],
            arg -> arg[1]
          ));
    }

    private static int forks(Map<String, String> parameters) {
        String forks = parameters.remove("forks");
        return parseOrDefault(forks, DEFAULT_FORKS);
    }

    private static int warmupIterations(Map<String, String> parameters) {
        String warmups = parameters.remove("warmupIterations");
        return parseOrDefault(warmups, DEFAULT_WARMUP_ITERATIONS);
    }

    private static int measurementIterations(Map<String, String> parameters) {
        String measurements = parameters.remove("measurementIterations");
        return parseOrDefault(measurements, DEFAULT_MEASUREMENT_ITERATIONS);
    }

    private static int parseOrDefault(String parameter, int defaultValue) {
        return parameter != null ? Integer.parseInt(parameter) : defaultValue;
    }

    @Benchmark
    public Object homemadeMatrixMultiplication(BigMatrixProvider matrixProvider) {
        return HomemadeMatrix.multiplyMatrices(matrixProvider.getFirstMatrix(), matrixProvider.getSecondMatrix());
    }

    @Benchmark
    public Object ejmlMatrixMultiplication(BigMatrixProvider matrixProvider) {
        SimpleMatrix firstMatrix = new SimpleMatrix(matrixProvider.getFirstMatrix());
        SimpleMatrix secondMatrix = new SimpleMatrix(matrixProvider.getSecondMatrix());

        return firstMatrix.mult(secondMatrix);
    }

    @Benchmark
    public Object apacheCommonsMatrixMultiplication(BigMatrixProvider matrixProvider) {
        RealMatrix firstMatrix = new Array2DRowRealMatrix(matrixProvider.getFirstMatrix());
        RealMatrix secondMatrix = new Array2DRowRealMatrix(matrixProvider.getSecondMatrix());

        return firstMatrix.multiply(secondMatrix);
    }

    @Benchmark
    public Object la4jMatrixMultiplication(BigMatrixProvider matrixProvider) {
        Matrix firstMatrix = new Basic2DMatrix(matrixProvider.getFirstMatrix());
        Matrix secondMatrix = new Basic2DMatrix(matrixProvider.getSecondMatrix());

        return firstMatrix.multiply(secondMatrix);
    }

    @Benchmark
    public Object nd4jMatrixMultiplication(BigMatrixProvider matrixProvider) {
        INDArray firstMatrix = Nd4j.create(matrixProvider.getFirstMatrix());
        INDArray secondMatrix = Nd4j.create(matrixProvider.getSecondMatrix());

        return firstMatrix.mmul(secondMatrix);
    }

    @Benchmark
    public Object coltMatrixMultiplication(BigMatrixProvider matrixProvider) {
        DoubleFactory2D doubleFactory2D = DoubleFactory2D.dense;

        DoubleMatrix2D firstMatrix = doubleFactory2D.make(matrixProvider.getFirstMatrix());
        DoubleMatrix2D secondMatrix = doubleFactory2D.make(matrixProvider.getSecondMatrix());

        Algebra algebra = new Algebra();
        return algebra.mult(firstMatrix, secondMatrix);
    }
}