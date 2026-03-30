package com.baeldung.regex;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class PatternSyntaxExceptionUnitTest {

    @Test
    void givenUnclosedCharacterClass_whenCompile_thenThrowException() {
        String regex = "[a-z";

        assertThrows(PatternSyntaxException.class, () -> {
            Pattern.compile(regex);
        });
    }

    @Test
    void givenValidCharacterClass_whenCompile_thenSuccess() {
        String regex = "[a-z]";

        Pattern pattern = Pattern.compile(regex);

        assertNotNull(pattern);
    }

    @Test
    void givenEscapedBracket_whenCompile_thenSuccess() {
        String regex = "[abc\\[]";

        assertDoesNotThrow(() -> Pattern.compile(regex));
    }

    @Test
    void givenInvalidRegex_whenValidate_thenReturnFalse() {
        assertFalse(RegexValidator.isValid("[a-z"));
    }

    @Test
    void givenValidRegex_whenValidate_thenReturnTrue() {
        assertTrue(RegexValidator.isValid("[a-z]"));
    }

    @Test
    void given2DArray_whenSplitWithEscapedRegex_thenSplitCorrectly() {
        String[][] array2d = {
                {"a", "b"},
                {"c", "d"}
        };

        String str = Arrays.deepToString(array2d);
        str = str.substring(1, str.length() - 1);

        String[] result = str.split("\\], \\[");

        assertEquals(2, result.length);
        assertTrue(result[0].contains("a"));
        assertTrue(result[1].contains("c"));
    }

    @Test
    void givenInvalidSplitRegex_whenSplit_thenThrowException() {
        String[][] array2d = {
                {"a", "b"},
                {"c", "d"}
        };

        String str = Arrays.deepToString(array2d);

        assertThrows(PatternSyntaxException.class, () -> {
            str.split("], [");
        });
    }

}
