package com.baeldung.numbers;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class StringToIntConversionUnitTest {

    StringToIntConversion stringToIntConversion = new StringToIntConversion();

    @Test
    void convertIntToStringUsingIntegerParseInt() {
        List<TestData> testData = Arrays.asList(
                new TestData("", null),
                new TestData(null, null),
                new TestData("23,56", null),
                new TestData("23", 23),
                new TestData("-23", -23),
                new TestData("2147483648", null),
                new TestData("-2147483649", null),
                new TestData("hello", null)
        );
        testData.forEach(data ->
                assertEquals(data.expectedOutput, stringToIntConversion.convertIntToStringUsingIntegerParseInt(data.input)));
    }

    @Test
    void ConvertIntToStringUsingIntegerValueOf() {
        List<TestData> testData = Arrays.asList(
                new TestData("", null),
                new TestData(null, null),
                new TestData("23,56", null),
                new TestData("23", 23),
                new TestData("-23", -23),
                new TestData("2147483648", null),
                new TestData("-2147483649", null),
                new TestData("hello", null)
        );
        testData.forEach(data ->
                assertEquals(data.expectedOutput, stringToIntConversion.convertIntToStringUsingIntegerValueOf(data.input)));
    }

    @Test
    void convertIntToStringUsingOptional() {
        List<TestData> testData = Arrays.asList(
                new TestData("", null),
                new TestData(null, null),
                new TestData("23,56", null),
                new TestData("23", 23),
                new TestData("-23", -23),
                new TestData("2147483648", null),
                new TestData("-2147483649", null),
                new TestData("hello", null)
        );
        testData.forEach(data ->
                assertEquals(data.expectedOutput, stringToIntConversion.convertIntToStringUsingOptional(data.input)));
    }

    @Test
    void convertIntToStringUsingNumberUtils() {
        List<TestData> testData = Arrays.asList(
                new TestData("", Integer.MIN_VALUE),
                new TestData(null, Integer.MIN_VALUE),
                new TestData("23,56", Integer.MIN_VALUE),
                new TestData("23", 23),
                new TestData("-23", -23),
                new TestData("2147483648", Integer.MIN_VALUE),
                new TestData("-2147483649", Integer.MIN_VALUE),
                new TestData("hello", Integer.MIN_VALUE)
        );
        testData.forEach(data ->
                assertEquals(data.expectedOutput, stringToIntConversion.convertIntToStringUsingNumberUtils(data.input)));
    }

    public static class TestData{
        String input;
        Integer expectedOutput;

        TestData(String input, Integer expectedOutput){
            this.input = input;
            this.expectedOutput = expectedOutput;
        }
    }
}