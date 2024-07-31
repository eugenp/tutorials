package com.baeldung.enums.extendenum;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExtendEnumUnitTest {
    private Application app = new Application();

    @Test
    public void givenAStringAndOperation_whenApplyOperation_thenGetExpectedResult() {
        String input = " hello";
        String expectedToUpper = " HELLO";
        String expectedReverse = "olleh ";
        String expectedTrim = "hello";
        String expectedBase64 = "IGhlbGxv";
        String expectedMd5 = "292a5af68d31c10e31ad449bd8f51263";
        assertEquals(expectedTrim, app.applyOperation(BasicStringOperation.TRIM, input));
        assertEquals(expectedToUpper, app.applyOperation(BasicStringOperation.TO_UPPER, input));
        assertEquals(expectedReverse, app.applyOperation(BasicStringOperation.REVERSE, input));
        assertEquals(expectedBase64, app.applyOperation(ExtendedStringOperation.BASE64_ENCODE, input));
        assertEquals(expectedMd5, app.applyOperation(ExtendedStringOperation.MD5_ENCODE, input));
    }

    @Test
    public void givenAStringAndImmutableOperation_whenApplyOperation_thenGetExpectedResult() {
        String input = " He ll O ";
        String expectedToLower = " he ll o ";
        String expectedRmWhitespace = "HellO";
        String expectedInvertCase = " hE LL o ";
        assertEquals(expectedToLower, app.applyImmutableOperation(ImmutableOperation.TO_LOWER, input));
        assertEquals(expectedRmWhitespace, app.applyImmutableOperation(ImmutableOperation.REMOVE_WHITESPACES, input));
        assertEquals(expectedInvertCase, app.applyImmutableOperation(ImmutableOperation.INVERT_CASE, input));
    }

    @Test
    public void givenUnmappedImmutableOperationValue_whenAppStarts_thenGetException() {
        Throwable throwable = assertThrows(ExceptionInInitializerError.class, () -> {
            ApplicationWithEx appEx = new ApplicationWithEx();
        });
        assertTrue(throwable.getCause() instanceof IllegalStateException);
    }
}