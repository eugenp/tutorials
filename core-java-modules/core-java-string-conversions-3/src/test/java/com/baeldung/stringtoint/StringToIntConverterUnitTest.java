package com.baeldung.stringtoint;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringToIntConverterUnitTest {

    @Test
    void whenConvertingIntToString_thenInvalidCasesReturnIntegerMinValue() {
        List<TestData> testData = Arrays.asList(
                new TestData("", Integer.MIN_VALUE),
                new TestData(null, Integer.MIN_VALUE),
                new TestData("23,56", Integer.MIN_VALUE),
                new TestData("2147483648", Integer.MIN_VALUE),
                new TestData("-2147483649", Integer.MIN_VALUE),
                new TestData("hello", Integer.MIN_VALUE)
        );
        testData.forEach(data -> {
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingIntegerParseInt(data.input));
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingIntegerValueOf(data.input));
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingOptional(data.input));
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingIntegerDecode(data.input));
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingNumberUtils(data.input));
        });
    }

    @Test
    void whenConvertingIntToString_thenValidCasesReturnUnboxedInt() {
        List<TestData> testData = Arrays.asList(
                new TestData("23", 23),
                new TestData("-23", -23)
        );
        testData.forEach(data -> {
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingIntegerParseInt(data.input));
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingIntegerValueOf(data.input));
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingOptional(data.input));
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingNumberUtils(data.input));
            Assertions.assertEquals(data.expectedOutput, StringToIntConverter.convertStringToIntUsingIntegerDecode(data.input));
        });
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
