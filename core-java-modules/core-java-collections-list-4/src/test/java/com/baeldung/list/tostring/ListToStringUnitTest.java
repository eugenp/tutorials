package com.baeldung.list.tostring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class ListToStringUnitTest {

    @Test
    void givenAListOfString_whenUsingJava8_thenConvertToStringByUsingString() {

        List<String> arraysAsList = Arrays.asList("ONE", "TWO", "THREE");

        String commaSeparatedString = String.join(",", arraysAsList);

        assertThat(commaSeparatedString).isEqualTo("ONE,TWO,THREE");
    }

    @Test
    void givenAListOfString_whenUsingJava8_thenConvertToStringByUsingStringJoiner() {

        List<String> arraysAsList = Arrays.asList("ONE", "TWO", "THREE");

        StringJoiner stringJoiner = new StringJoiner(",");
        arraysAsList.stream()
            .forEach(v -> stringJoiner.add(v));
        String commaSeparatedString = stringJoiner.toString();

        assertThat(commaSeparatedString).isEqualTo("ONE,TWO,THREE");

        StringJoiner stringJoinerWithDelimiterPrefixSuffix = new StringJoiner(",", "[", "]");
        arraysAsList.stream()
            .forEach(v -> stringJoinerWithDelimiterPrefixSuffix.add(v));
        String commaSeparatedStringWithDelimiterPrefixSuffix = stringJoinerWithDelimiterPrefixSuffix.toString();

        assertThat(commaSeparatedStringWithDelimiterPrefixSuffix).isEqualTo("[ONE,TWO,THREE]");
    }

    @Test
    void givenAListOfString_whenUsingJava8_thenConvertToStringByUsingCollectors() {

        List<String> arraysAsList = Arrays.asList("ONE", "TWO", "THREE");

        String commaSeparatedUsingCollect = arraysAsList.stream()
            .collect(Collectors.joining(","));

        assertThat(commaSeparatedUsingCollect).isEqualTo("ONE,TWO,THREE");

        String commaSeparatedObjectToString = arraysAsList.stream()
            .map(Object::toString)
            .collect(Collectors.joining(","));

        assertThat(commaSeparatedObjectToString).isEqualTo("ONE,TWO,THREE");

        String commaSeparatedStringValueOf = arraysAsList.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));

        assertThat(commaSeparatedStringValueOf).isEqualTo("ONE,TWO,THREE");

        String commaSeparatedStringValueOfWithDelimiterPrefixSuffix = arraysAsList.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(",", "[", "]"));

        assertThat(commaSeparatedStringValueOfWithDelimiterPrefixSuffix).isEqualTo("[ONE,TWO,THREE]");

        String commaSeparatedUsingReduce = arraysAsList.stream()
            .reduce((x, y) -> x + "," + y)
            .get();

        assertThat(commaSeparatedUsingReduce).isEqualTo("ONE,TWO,THREE");
    }

    @Test
    void givenAListOfString_whenUsingApacheCommonsLang_thenConvertToStringByUsingStringUtils() {

        List<String> arraysAsList = Arrays.asList("ONE", "TWO", "THREE");

        String commaSeparatedString = org.apache.commons.lang3.StringUtils.join(arraysAsList, ",");

        assertThat(commaSeparatedString).isEqualTo("ONE,TWO,THREE");

        String commaSeparatedStringIndex = org.apache.commons.lang3.StringUtils.join(arraysAsList.toArray(), ",", 0, 3);

        assertThat(commaSeparatedStringIndex).isEqualTo("ONE,TWO,THREE");
    }

    @Test
    void givenAListOfString_whenUsingSpringCore_thenConvertToStringByUsingStringUtils() {

        List<String> arraysAsList = Arrays.asList("ONE", "TWO", "THREE");

        String collectionToCommaDelimitedString = org.springframework.util.StringUtils.collectionToCommaDelimitedString(arraysAsList);

        assertThat(collectionToCommaDelimitedString).isEqualTo("ONE,TWO,THREE");

        String collectionToDelimitedString = org.springframework.util.StringUtils.collectionToDelimitedString(arraysAsList, ",");

        assertThat(collectionToDelimitedString).isEqualTo("ONE,TWO,THREE");
    }

    @Test
    void givenAListOfString_whenUsingGoogleGuava_thenConvertToStringByUsingJoiner() {

        List<String> arraysAsList = Arrays.asList("ONE", "TWO", "THREE");

        String commaSeparatedString = com.google.common.base.Joiner.on(",")
            .join(arraysAsList);

        assertThat(commaSeparatedString).isEqualTo("ONE,TWO,THREE");

        List<String> arraysAsListWithNull = Arrays.asList("ONE", null, "TWO", null, "THREE");

        String commaSeparatedStringSkipNulls = com.google.common.base.Joiner.on(",")
            .skipNulls()
            .join(arraysAsListWithNull);

        assertThat(commaSeparatedStringSkipNulls).isEqualTo("ONE,TWO,THREE");

        String commaSeparatedStringUseForNull = com.google.common.base.Joiner.on(",")
            .useForNull(" ")
            .join(arraysAsListWithNull);

        assertThat(commaSeparatedStringUseForNull).isEqualTo("ONE, ,TWO, ,THREE");
    }

}
