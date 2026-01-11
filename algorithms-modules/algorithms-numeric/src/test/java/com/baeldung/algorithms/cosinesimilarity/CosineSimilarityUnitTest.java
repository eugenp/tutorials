package com.baeldung.algorithms.cosinesimilarity;

import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.reduce3.CosineSimilarity;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CosineSimilarityUnitTest {

    static final double[] VECTOR_A = {3, 4};
    static final double[] VECTOR_B = {5, 12};
    static final double EXPECTED_SIMILARITY = 0.9692307692307692;

    static double calculateCosineSimilarity(double[] vectorA, double[] vectorB) {
        if (vectorA == null || vectorB == null || vectorA.length != vectorB.length || vectorA.length == 0) {
            throw new IllegalArgumentException("Vectors must be non-null, non-empty, and of the same length.");
        }
        double dotProduct = 0.0;
        double magnitudeA = 0.0;
        double magnitudeB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            magnitudeA += vectorA[i] * vectorA[i];
            magnitudeB += vectorB[i] * vectorB[i];
        }
        double finalMagnitudeA = Math.sqrt(magnitudeA);
        double finalMagnitudeB = Math.sqrt(magnitudeB);
        if (finalMagnitudeA == 0.0 || finalMagnitudeB == 0.0) {
            return 0.0;
        }
        return dotProduct / (finalMagnitudeA * finalMagnitudeB);
    }

    public static double calculateCosineSimilarityWithStreams(double[] vectorA, double[] vectorB) {
        if (vectorA == null || vectorB == null || vectorA.length != vectorB.length || vectorA.length == 0) {
            throw new IllegalArgumentException("Vectors must be non-null, non-empty, and of the same length.");
        }

        double dotProduct = IntStream.range(0, vectorA.length).mapToDouble(i -> vectorA[i] * vectorB[i]).sum();
        double magnitudeA = Arrays.stream(vectorA).map(v -> v * v).sum();
        double magnitudeB = IntStream.range(0, vectorA.length).mapToDouble(i -> vectorB[i] * vectorB[i]).sum();
        double finalMagnitudeA = Math.sqrt(magnitudeA);
        double finalMagnitudeB = Math.sqrt(magnitudeB);
        if (finalMagnitudeA == 0.0 || finalMagnitudeB == 0.0) {
            return 0.0;
        }

        return dotProduct / (finalMagnitudeA * finalMagnitudeB);
    }

    @Test
    void givenTwoHighlySimilarVectors_whenCalculatedNatively_thenReturnsHighSimilarityScore() {
        double actualSimilarity = calculateCosineSimilarity(VECTOR_A, VECTOR_B);
        assertEquals(EXPECTED_SIMILARITY, actualSimilarity, 1e-15);
    }

    @Test
    void givenTwoHighlySimilarVectors_whenCalculatedNativelyWithStreams_thenReturnsHighSimilarityScore() {
        double actualSimilarity = calculateCosineSimilarityWithStreams(VECTOR_A, VECTOR_B);
        assertEquals(EXPECTED_SIMILARITY, actualSimilarity, 1e-15);
    }

    @Test
    void givenTwoHighlySimilarVectors_whenCalculatedNativelyWithCommonsMath_thenReturnsHighSimilarityScore() {

        INDArray vec1 = Nd4j.create(VECTOR_A);
        INDArray vec2 = Nd4j.create(VECTOR_B);

        CosineSimilarity cosSim = new CosineSimilarity(vec1, vec2);
        double actualSimilarity = Nd4j.getExecutioner().exec(cosSim).getDouble(0);

        assertEquals(EXPECTED_SIMILARITY, actualSimilarity, 1e-15);
    }

}