package com.baeldung.join;

import com.google.common.base.Joiner;
import com.google.common.primitives.Chars;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class StringFromPrimitiveArrayUnitTest {

    private int[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    private char[] charArray = {'a', 'b', 'c', 'd', 'e', 'f'};

    private char separatorChar = '-';

    private String separator = String.valueOf(separatorChar);

    private String expectedIntString = "1-2-3-4-5-6-7-8-9";

    private String expectedCharString = "a-b-c-d-e-f";

    @Test
    public void givenIntArray_whenJoinBySeparator_thenReturnsString_through_Java8CollectorsJoining() {
        assertThat(Arrays.stream(intArray)
          .mapToObj(String::valueOf)
          .collect(Collectors.joining(separator)))
          .isEqualTo(expectedIntString);
    }

    @Test
    public void givenCharArray_whenJoinBySeparator_thenReturnsString_through_Java8CollectorsJoining() {
        assertThat(CharBuffer.wrap(charArray).chars()
          .mapToObj(intChar -> String.valueOf((char) intChar))
          .collect(Collectors.joining(separator)))
          .isEqualTo(expectedCharString);
    }


    @Test
    public void giveIntArray_whenJoinBySeparator_thenReturnsString_through_Java8StringJoiner() {
        StringJoiner intStringJoiner = new StringJoiner(separator);

        Arrays.stream(intArray)
          .mapToObj(String::valueOf)
          .forEach(intStringJoiner::add);

        assertThat(intStringJoiner.toString()).isEqualTo(expectedIntString);
    }

    @Test
    public void givenCharArray_whenJoinBySeparator_thenReturnsString_through_Java8StringJoiner() {
        StringJoiner charStringJoiner = new StringJoiner(separator);

        CharBuffer.wrap(charArray).chars()
          .mapToObj(intChar -> String.valueOf((char) intChar))
          .forEach(charStringJoiner::add);

        assertThat(charStringJoiner.toString()).isEqualTo(expectedCharString);
    }

    @Test
    public void givenIntArray_whenJoinBySeparator_thenReturnsString_through_CommonsLang() {
        assertThat(StringUtils.join(intArray, separatorChar)).isEqualTo(expectedIntString);
        assertThat(StringUtils.join(ArrayUtils.toObject(intArray), separator)).isEqualTo(expectedIntString);
    }

    @Test
    public void givenCharArray_whenJoinBySeparator_thenReturnsString_through_CommonsLang() {
        assertThat(StringUtils.join(charArray, separatorChar)).isEqualTo(expectedCharString);
        assertThat(StringUtils.join(ArrayUtils.toObject(charArray), separator)).isEqualTo(expectedCharString);
    }

    @Test
    public void givenIntArray_whenJoinBySeparator_thenReturnsString_through_GuavaJoiner() {
        assertThat(Joiner.on(separator).join(Ints.asList(intArray))).isEqualTo(expectedIntString);
    }

    @Test
    public void givenCharArray_whenJoinBySeparator_thenReturnsString_through_GuavaJoiner() {
        assertThat(Joiner.on(separator).join(Chars.asList(charArray))).isEqualTo(expectedCharString);
    }

    @Test
    public void givenIntArray_whenJoinBySeparator_thenReturnsString_through_Java7StringBuilder() {
        assertThat(joinIntArrayWithStringBuilder(intArray, separator)).isEqualTo(expectedIntString);
    }

    @Test
    public void givenCharArray_whenJoinBySeparator_thenReturnsString_through_Java7StringBuilder() {
        assertThat(joinCharArrayWithStringBuilder(charArray, separator)).isEqualTo(expectedCharString);
    }

    private String joinIntArrayWithStringBuilder(int[] array, String separator) {
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

    private String joinCharArrayWithStringBuilder(char[] array, String separator) {
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
