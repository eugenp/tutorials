package com.baeldung.stringtoint;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringToIntConverterUnitTest {
    
    private StringToIntConverter stringToIntConverter = new StringToIntConverter();

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
            Assertions.assertEquals(data.expectedOutput, stringToIntConverter.convertStringToIntUsingIntegerParseInt(data.input).orElse(Integer.MIN_VALUE));
            Assertions.assertEquals(data.expectedOutput, stringToIntConverter.convertStringToIntUsingIntegerValueOf(data.input).orElse(Integer.MIN_VALUE));
            Assertions.assertEquals(data.expectedOutput, stringToIntConverter.convertStringToIntUsingIntegerDecode(data.input).orElse(Integer.MIN_VALUE));
            Assertions.assertEquals(data.expectedOutput, stringToIntConverter.convertStringToIntUsingNumberUtils(data.input,Integer.MIN_VALUE ));
        });
    }

    @Test
    void whenConvertingIntToString_thenValidCasesReturnUnboxedInt() {
        List<TestData> testData = Arrays.asList(
                new TestData("23", 23),
                new TestData("-23", -23)
        );
        testData.forEach(data -> {
            Assertions.assertEquals(data.expectedOutput, stringToIntConverter.convertStringToIntUsingIntegerParseInt(data.input).orElse(Integer.MIN_VALUE));
            Assertions.assertEquals(data.expectedOutput, stringToIntConverter.convertStringToIntUsingIntegerValueOf(data.input).orElse(Integer.MIN_VALUE));
            Assertions.assertEquals(data.expectedOutput, stringToIntConverter.convertStringToIntUsingNumberUtils(data.input, Integer.MIN_VALUE));
            Assertions.assertEquals(data.expectedOutput, stringToIntConverter.convertStringToIntUsingIntegerDecode(data.input).orElse(Integer.MIN_VALUE));
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
