package com.baeldung.array.conversions;

import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ByteArrayToPrimitiveByteArrayUnitTest {
    public static byte[] expectedArrayValues = {65, 66, 67, 68};
    public static Byte[] byteArray = {65, 66, 67, 68};

    @Test
    void givenByteArray_whenConvertingUsingByteValue_thenGiveExpectedResult() {
        byte[] newByteArray = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            newByteArray[i] = byteArray[i].byteValue();
        }
        Assertions.assertThat(newByteArray)
          .containsExactly(expectedArrayValues);
    }

    @Test
    void givenByteArray_whenConvertingUsingUnboxing_thenGiveExpectedResult() {
        byte[] newByteArray = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            newByteArray[i] = byteArray[i];
        }
        Assertions.assertThat(newByteArray)
          .containsExactly(expectedArrayValues);
    }

    @Test
    void givenByteArray_whenConvertingArrayUtils_thenGiveExpectedResult() {
        byte[] newByteArray = ArrayUtils.toPrimitive(byteArray);

        Assertions.assertThat(newByteArray)
          .containsExactly(expectedArrayValues);
    }
}
