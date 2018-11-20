package com.baeldung.string;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StringFromPrimitiveArrayTest {

    private static final int[] PRIMITIVE_ARRAY = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    private static final char SEPARATOR_CHAR = '-';

    private static final String SEPARATOR = String.valueOf(SEPARATOR_CHAR);

    private static final String EXPECTED_STRING = "1-2-3-4-5-6-7-8-9";

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_Java8CollectorsJoining() {
        assertThat(Arrays.stream(PRIMITIVE_ARRAY)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(SEPARATOR))).isEqualTo(EXPECTED_STRING);
    }

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_Java8StringJoiner() {
        StringJoiner stringJoiner = new StringJoiner(SEPARATOR);
        Arrays.stream(PRIMITIVE_ARRAY)
                .mapToObj(String::valueOf)
                .forEach(stringJoiner::add);

        assertThat(stringJoiner.toString()).isEqualTo(EXPECTED_STRING);
    }

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_CommonsLang() {
        assertThat(StringUtils.join(PRIMITIVE_ARRAY, SEPARATOR_CHAR)).isEqualTo(EXPECTED_STRING);
        assertThat(StringUtils.join(ArrayUtils.toObject(PRIMITIVE_ARRAY), SEPARATOR)).isEqualTo(EXPECTED_STRING);
    }

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_GuavaJoiner() {
        assertThat(Joiner.on(SEPARATOR).join(Ints.asList(PRIMITIVE_ARRAY))).isEqualTo(EXPECTED_STRING);
    }

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_Java7StringBuilder() {
        assertThat(joinWithStringBuilder(PRIMITIVE_ARRAY, SEPARATOR)).isEqualTo(EXPECTED_STRING);
    }

    private String joinWithStringBuilder(int[] array, String separator) {
        if (array.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length - 1; i++) {
            stringBuilder.append(array[i]);
            stringBuilder.append(separator);
        }
        stringBuilder.append(array[array.length - 1]);
        return stringBuilder.toString();
    }
}
