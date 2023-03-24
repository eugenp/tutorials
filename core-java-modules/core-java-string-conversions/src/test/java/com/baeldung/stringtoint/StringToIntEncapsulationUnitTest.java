package com.baeldung.stringtoint;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringToIntEncapsulationUnitTest {

    @Test
    void whenConvertingIntToString_thenInvalidCasesReturnNull() {
        List<TestData> testData = Arrays.asList(
                new TestData("", null),
                new TestData(null, null),
                new TestData("23,56", null),
                new TestData("2147483648", null),
                new TestData("-2147483649", null),
                new TestData("hello", null)
        );
        testData.forEach(data -> {
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.convertStringToIntUsingIntegerParseInt(data.input));
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.convertStringToIntUsingIntegerValueOf(data.input));
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.converStringToIntUsingOptional(data.input));
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.convertStringToIntUsingIntegerDecode(data.input));
        });
    }

    @Test
    void whenConvertingIntToString_thenValidCasesReturnUnboxedInt() {
        List<TestData> testData = Arrays.asList(
                new TestData("23", 23),
                new TestData("-23", -23)
        );
        testData.forEach(data -> {
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.convertStringToIntUsingIntegerParseInt(data.input));
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.convertStringToIntUsingIntegerValueOf(data.input));
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.converStringToIntUsingOptional(data.input));
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.convertStringToIntUsingNumberUtils(data.input));
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.convertStringToIntUsingIntegerDecode(data.input));
        });
    }

    @Test
    void whenConvertingStringToIntUsingNumberUtils_ThenInValidConversionReturnIntegerMin() {
        List<TestData> testData = Arrays.asList(
                new TestData("", Integer.MIN_VALUE),
                new TestData(null, Integer.MIN_VALUE),
                new TestData("23,56", Integer.MIN_VALUE),
                new TestData("2147483648", Integer.MIN_VALUE),
                new TestData("-2147483649", Integer.MIN_VALUE),
                new TestData("hello", Integer.MIN_VALUE)
        );
        testData.forEach(data ->
                Assertions.assertEquals(data.expectedOutput, StringToIntEncapsulation.convertStringToIntUsingNumberUtils(data.input)));
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