package com.baeldung.array.conversions;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PrimitiveByteArrayToByteArrayUnitTest {
    private static final byte[] PRIMITIVE_BYTE_ARRAY = {65, 66, 67, 68};
    private static final Byte[] EXPECTED_ARRAY_VALUES = {65, 66, 67, 68};

    @Test
    void givenPrimitiveByteArray_whenConvertingUsingByteValueOf_thenGiveExpectedResult() {
        Byte[] newByteArray = new Byte[PRIMITIVE_BYTE_ARRAY.length];
        for (int i = 0; i < PRIMITIVE_BYTE_ARRAY.length; i++) {
            newByteArray[i] = Byte.valueOf(PRIMITIVE_BYTE_ARRAY[i]);
        }
        Assertions.assertThat(newByteArray)
          .containsExactly(EXPECTED_ARRAY_VALUES);
    }

    @Test
    void givenPrimitiveByteArray_whenConvertingUsingAutoboxing_thenGiveExpectedResult() {
        Byte[] newByteArray = new Byte[PRIMITIVE_BYTE_ARRAY.length];
        for (int i = 0; i < PRIMITIVE_BYTE_ARRAY.length; i++) {
            newByteArray[i] = PRIMITIVE_BYTE_ARRAY[i];
        }
        Assertions.assertThat(newByteArray)
            .containsExactly(EXPECTED_ARRAY_VALUES);
    }

    @Test
    void givenPrimitiveByteArray_whenConvertingUsingAutoboxingAndArraysSetAll_thenGiveExpectedResult() {
        Byte[] newByteArray = new Byte[PRIMITIVE_BYTE_ARRAY.length];
        Arrays.setAll(newByteArray, n -> PRIMITIVE_BYTE_ARRAY[n]);

        Assertions.assertThat(newByteArray)
            .containsExactly(EXPECTED_ARRAY_VALUES);
    }

    @Test
    void givenPrimitiveByteArray_whenConvertingUsingArrayUtils_thenGiveExpectedResult() {
        Byte[] newByteArray = ArrayUtils.toObject(PRIMITIVE_BYTE_ARRAY);

        Assertions.assertThat(newByteArray)
            .containsExactly(EXPECTED_ARRAY_VALUES);
    }
}
