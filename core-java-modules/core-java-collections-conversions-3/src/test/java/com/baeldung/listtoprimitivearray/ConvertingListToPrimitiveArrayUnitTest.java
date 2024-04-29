package com.baeldung.listtoprimitivearray;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ConvertingListToPrimitiveArrayUnitTest {

    @ParameterizedTest
    @MethodSource("floatListProvider")
    void givenListOfWrapperFloat_whenConvertToWrapperArray_thenGetCorrectResult(List<Float> floats) {
        Float[] actual = floats.toArray(new Float[0]);
        assertSequences(floats, actual);
    }

    @ParameterizedTest
    @MethodSource("floatListProvider")
    void givenListOfWrapperFloat_whenConvertToWrapperArrayWithPreSizedArray_thenGetCorrectResult(List<Float> floats) {
        Float[] actual = floats.toArray(new Float[floats.size()]);
        assertSequences(floats, actual);
    }

    @ParameterizedTest
    @MethodSource("floatListProvider")
    void givenListOfWrapperFloat_whenConvertToPrimitiveArray_thenGetCorrectResult(List<Float> floats) {
        float[] actual = new float[floats.size()];
        for (int i = 0; i < floats.size(); i++) {
            actual[i] = floats.get(i);
        }
        assertSequences(floats, actual);
    }

    @ParameterizedTest
    @MethodSource("floatListProvider")
    void givenListOfWrapperFloat_whenUnboxToPrimitiveArray_thenGetCorrectResult(List<Float> floats) {
        float[] actual = new float[floats.size()];
        Float[] floatArray = floats.toArray(new Float[0]);
        for (int i = 0; i < floats.size(); i++) {
            actual[i] = floatArray[i];
        }
        assertSequences(floats, actual);
    }

    @ParameterizedTest
    @MethodSource("floatListProvider")
    void givenListOfWrapperFloat_whenConvertToPrimitiveArrayWithArrayUtils_thenGetCorrectResult(List<Float> floats) {
        float[] actual = ArrayUtils.toPrimitive(floats.toArray(new Float[]{}));
        assertSequences(floats, actual);
    }

    @ParameterizedTest
    @MethodSource("floatListProvider")
    void givenListOfWrapperFloat_whenConvertingToPrimitiveArrayUsingStreams_thenGetCorrectResult(List<Float> floats) {
        double[] actual = floats.stream().mapToDouble(Float::doubleValue).toArray();
        assertSequences(floats, actual);
    }

    @ParameterizedTest
    @MethodSource("floatListProvider")
    void givenListOfWrapperFloat_whenConvertingWithCollector_thenGetCorrectResult(List<Float> floats) {
        float[] actual = floats.stream().collect(new FloatCollector(floats.size()));
        assertSequences(floats, actual);
    }

    private static void assertSequences(Iterable<Float> floats, Float[] actual) {
        assertThat(actual).hasSameSizeAs(floats);
        for (Float value : floats) {
            assertThat(actual).contains(value);
        }
    }

    private static void assertSequences(Iterable<Float> floats, float[] actual) {
        assertThat(actual).hasSameSizeAs(floats);
        for (Float value : floats) {
            assertThat(actual).contains(value);
        }
    }

    private static void assertSequences(Iterable<Float> floats, double[] actual) {
        assertThat(actual).hasSameSizeAs(floats);
        for (Float value : floats) {
            assertThat(actual).contains(value);
        }
    }

    static Stream<List<Float>> floatListProvider() {
        ArrayList<Float> floats = new ArrayList<>();
        floats.add(Float.valueOf(131.36f));
        floats.add(Float.valueOf(66.59f));
        floats.add(Float.valueOf(58.03f));
        floats.add(Float.valueOf(951.56f));
        floats.add(Float.valueOf(192.04f));
        floats.add(Float.valueOf(899.03f));
        floats.add(Float.valueOf(147.32f));
        floats.add(Float.valueOf(573.75f));
        floats.add(Float.valueOf(94.75f));
        return Stream.of(floats);
    }
}
