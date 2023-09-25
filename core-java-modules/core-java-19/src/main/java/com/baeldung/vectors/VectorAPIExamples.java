package com.baeldung.vectors;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

/**
 * This class contains examples of the Vector API in Java, more specifically the fourth incubator to the Vector API.
 * There are scalar and vector methods written here and the method names will specify the type.
 * Note that, the code is written without any benchmarking done and without any assumption of it being run on
 * a SIMD processor, or any specific compiler.
 */
public class VectorAPIExamples {

    static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;
    static final VectorSpecies<Float> PREFERRED_SPECIES = FloatVector.SPECIES_PREFERRED;

    public int[] addTwoScalarArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i] + arr2[i];
        }
        return result;
    }

    public int[] addTwoVectorArrays(int[] arr1, int[] arr2) {
        var v1 = IntVector.fromArray(SPECIES, arr1, 0);
        var v2 = IntVector.fromArray(SPECIES, arr2, 0);
        var result = v1.add(v2);
        return result.toArray();
    }

    public int[] addTwoVectorsWIthHigherShape(int[] arr1, int[] arr2) {
        int[] finalResult = new int[arr1.length];
        for (int i = 0; i < arr1.length; i += SPECIES.length()) {
            var v1 = IntVector.fromArray(SPECIES, arr1, i);
            var v2 = IntVector.fromArray(SPECIES, arr2, i);
            var result = v1.add(v2);
            result.intoArray(finalResult, i);
        }
        return finalResult;
    }

    public int[] addTwoVectorsWithMasks(int[] arr1, int[] arr2) {
        int[] finalResult = new int[arr1.length];
        int i = 0;
        for (; i < SPECIES.loopBound(arr1.length); i += SPECIES.length()) {
            var mask = SPECIES.indexInRange(i, arr1.length);
            var v1 = IntVector.fromArray(SPECIES, arr1, i, mask);
            var v2 = IntVector.fromArray(SPECIES, arr2, i, mask);
            var result = v1.add(v2, mask);
            result.intoArray(finalResult, i, mask);
        }

        // tail cleanup loop
        for (; i < arr1.length; i++) {
            finalResult[i] = arr1[i] + arr2[i];
        }
        return finalResult;
    }

    public float[] scalarNormOfTwoArrays(float[] arr1, float[] arr2) {
        float[] finalResult = new float[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            finalResult[i] = (arr1[i] * arr1[i] + arr2[i] * arr2[i]) * -1.0f;
        }
        return finalResult;
    }

    public float[] vectorNormalForm(float[] arr1, float[] arr2) {
        float[] finalResult = new float[arr1.length];
        int i = 0;
        int upperBound = SPECIES.loopBound(arr1.length);
        for (; i < upperBound; i += SPECIES.length()) {
            var va = FloatVector.fromArray(PREFERRED_SPECIES, arr1, i);
            var vb = FloatVector.fromArray(PREFERRED_SPECIES, arr2, i);
            var vc = va.mul(va)
              .add(vb.mul(vb))
              .neg();
            vc.intoArray(finalResult, i);
        }

        // tail cleanup
        for (; i < arr1.length; i++) {
            finalResult[i] = (arr1[i] * arr1[i] + arr2[i] * arr2[i]) * -1.0f;
        }
        return finalResult;
    }

    public double averageOfaVector(int[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i += SPECIES.length()) {
            var mask = SPECIES.indexInRange(i, arr.length);
            var V = IntVector.fromArray(SPECIES, arr, i, mask);
            sum += V.reduceLanes(VectorOperators.ADD, mask);
        }
        return sum / arr.length;
    }
}
