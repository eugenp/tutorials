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

    private int[] testPrimitiveArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    private char separatorChar = '-';

    private String separator = String.valueOf(separatorChar);

    private String expectedString = "1-2-3-4-5-6-7-8-9";

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_Java8CollectorsJoining() {
        assertThat(Arrays.stream(testPrimitiveArray)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(separator))).isEqualTo(expectedString);
    }

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_Java8StringJoiner() {
        StringJoiner stringJoiner = new StringJoiner(separator);
        Arrays.stream(testPrimitiveArray)
                .mapToObj(String::valueOf)
                .forEach(stringJoiner::add);

        assertThat(stringJoiner.toString()).isEqualTo(expectedString);
    }

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_CommonsLang() {
        assertThat(StringUtils.join(testPrimitiveArray, separatorChar)).isEqualTo(expectedString);
        assertThat(StringUtils.join(ArrayUtils.toObject(testPrimitiveArray), separator)).isEqualTo(expectedString);
    }

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_GuavaJoiner() {
        assertThat(Joiner.on(separator).join(Ints.asList(testPrimitiveArray))).isEqualTo(expectedString);
    }

    @Test
    public void givenPrimitiveArray_whenJoinBySeparator_thenReturnsString_through_Java7StringBuilder() {
        assertThat(joinWithStringBuilder(testPrimitiveArray, separator)).isEqualTo(expectedString);
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
