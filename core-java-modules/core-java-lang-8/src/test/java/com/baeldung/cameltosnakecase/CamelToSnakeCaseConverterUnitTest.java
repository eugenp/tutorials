package com.baeldung.cameltosnakecase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CamelToSnakeCaseConverterUnitTest {

    @Test
    public void whenConvertNormalCamelCase_thenGetCorrectSnakeCase() {
        String input = "convertCamelCase";
        String expected = "convert_camel_case";
        Assertions.assertEquals(expected, CamelToSnakeCaseConverter.convertCamelCaseToSnake(input));
        Assertions.assertEquals(expected, CamelToSnakeCaseConverter.convertCamelCaseToSnakeRegex(input));
    }

    @Test
    public void whenConvertAlreadySnakeCase_thenGetUnchangedSnakeCase() {
        String input = "snake_case";
        String expected = "snake_case";
        Assertions.assertEquals(expected, CamelToSnakeCaseConverter.convertCamelCaseToSnake(input));
        Assertions.assertEquals(expected, CamelToSnakeCaseConverter.convertCamelCaseToSnakeRegex(input));
    }

    @Test
    public void whenConvertAllLowerCaseString_thenGetUnchangedString() {
        String input = "snakecase";
        String expected = "snakecase";
        Assertions.assertEquals(expected, CamelToSnakeCaseConverter.convertCamelCaseToSnake(input));
        Assertions.assertEquals(expected, CamelToSnakeCaseConverter.convertCamelCaseToSnakeRegex(input));
    }

    @Test
    public void whenConvertOtherEdgeCases_thenGetCorrectSnakeCases() {
        // Blank string
        Assertions.assertEquals("", CamelToSnakeCaseConverter.convertCamelCaseToSnake(""));
        Assertions.assertEquals("", CamelToSnakeCaseConverter.convertCamelCaseToSnakeRegex(""));

        // Special character
        String input = "sn@keCase#";
        String expected = "sn@ke_case#";
        Assertions.assertEquals(expected, CamelToSnakeCaseConverter.convertCamelCaseToSnake(input));
        Assertions.assertEquals(expected, CamelToSnakeCaseConverter.convertCamelCaseToSnakeRegex(input));
    }
}