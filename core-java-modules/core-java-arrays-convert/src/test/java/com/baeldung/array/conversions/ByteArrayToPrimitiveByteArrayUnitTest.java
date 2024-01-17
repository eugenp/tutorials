package com.baeldung.array.conversions;

import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ByteArrayToPrimitiveByteArrayUnitTest {
    private static final byte[] EXPECTED_ARRAY_VALUES = {65, 66, 67, 68};
    private static final Byte[] BYTE_ARRAY = {65, 66, 67, 68};

    @Test
    void givenByteArray_whenConvertingUsingByteValue_thenGiveExpectedResult() {
        byte[] newByteArray = new byte[BYTE_ARRAY.length];
        for (int i = 0; i < BYTE_ARRAY.length; i++) {
            newByteArray[i] = BYTE_ARRAY[i].byteValue();
        }
        Assertions.assertThat(newByteArray)
          .containsExactly(EXPECTED_ARRAY_VALUES);
    }

    @Test
    void givenByteArray_whenConvertingUsingUnboxing_thenGiveExpectedResult() {
        byte[] newByteArray = new byte[BYTE_ARRAY.length];
        for (int i = 0; i < BYTE_ARRAY.length; i++) {
            newByteArray[i] = BYTE_ARRAY[i];
        }
        Assertions.assertThat(newByteArray)
          .containsExactly(EXPECTED_ARRAY_VALUES);
    }

    @Test
    void givenByteArray_whenConvertingArrayUtils_thenGiveExpectedResult() {
        byte[] newByteArray = ArrayUtils.toPrimitive(BYTE_ARRAY);

        Assertions.assertThat(newByteArray)
          .containsExactly(EXPECTED_ARRAY_VALUES);
    }
}
