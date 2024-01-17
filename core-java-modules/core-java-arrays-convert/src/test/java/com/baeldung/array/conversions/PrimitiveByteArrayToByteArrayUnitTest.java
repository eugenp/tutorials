package com.baeldung.array.conversions;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PrimitiveByteArrayToByteArrayUnitTest {
    public static byte[] primitiveByteArray = {65, 66, 67, 68};
    public static Byte[] expectedArrayValues = {65, 66, 67, 68};

    @Test
    void givenPrimitiveByteArray_whenConvertingUsingByteValueOf_thenGiveExpectedResult() {
        Byte[] newByteArray = new Byte[primitiveByteArray.length];
        for (int i = 0; i < primitiveByteArray.length; i++) {
            newByteArray[i] = Byte.valueOf(primitiveByteArray[i]);
        }
        Assertions.assertThat(newByteArray)
          .containsExactly(expectedArrayValues);
    }

    @Test
    void givenPrimitiveByteArray_whenConvertingUsingAutoboxing_thenGiveExpectedResult() {
        Byte[] newByteArray = new Byte[primitiveByteArray.length];
        for (int i = 0; i < primitiveByteArray.length; i++) {
            newByteArray[i] = primitiveByteArray[i];
        }
        Assertions.assertThat(newByteArray)
            .containsExactly(expectedArrayValues);
    }

    @Test
    void givenPrimitiveByteArray_whenConvertingUsingAutoboxingAndArraysSetAll_thenGiveExpectedResult() {
        Byte[] newByteArray = new Byte[primitiveByteArray.length];
        Arrays.setAll(newByteArray, n -> primitiveByteArray[n]);

        Assertions.assertThat(newByteArray)
            .containsExactly(expectedArrayValues);
    }

    @Test
    void givenPrimitiveByteArray_whenConvertingUsingArrayUtils_thenGiveExpectedResult() {
        Byte[] newByteArray = ArrayUtils.toObject(primitiveByteArray);

        Assertions.assertThat(newByteArray)
            .containsExactly(expectedArrayValues);
    }
}
