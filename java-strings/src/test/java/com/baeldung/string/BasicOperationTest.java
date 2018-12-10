package com.baeldung.string;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.CharacterIterator;
import java.util.Arrays;
import java.util.Collections;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.junit.Assert.*;

public class BasicOperationTest {

    @Test
    public void givenAChar_whenToStringWithValueOfIsCalled_thenWeGetTheCharAsString() {
        assertEquals("a", BasicOperation.toStringWithValueOf('a'));
        assertEquals("b", BasicOperation.toStringWithValueOf('b'));
    }

    @Test
    public void givenAChar_whenToStringWithConcatenationIsCalled_thenWeGetTheCharAsString() {
        assertEquals("a", BasicOperation.toStringWithConcatenation('a'));
        assertEquals("b", BasicOperation.toStringWithConcatenation('b'));
    }

    @Test
    public void givenAString_whenCharAtIsCalled_thenWeGetTheChar() {
        assertEquals('a', BasicOperation.getChar("Hallo", 1));
        assertEquals('a', BasicOperation.getChar("Hallo baeldung", 7));
    }

    @Test
    public void givenAString_whenANewStringCharacterIteratorIsCreated_thenWeGetAChacacterIterator() {
        assertTrue("Is a chacaterIterator", BasicOperation.getIterator("Hallo") instanceof CharacterIterator);
        assertTrue("Is a chacaterIterator", BasicOperation.getIterator("") instanceof CharacterIterator);
    }

    @Test
    public void givenAStringAndAChar_whenThePlusOperatorIsCalled_thenTheCharIsAppended() {
        assertEquals("Hello baeldung!", BasicOperation.append("Hello baeldung", '!'));
        assertEquals("?", BasicOperation.append("", '?'));
    }

    @Test
    public void givenAStringWithWhiteSpace_whenTheReplaceMethodIsCalledWithARegExForWhiteSpace_thenAllWhiteSpaceIsRemoved() {
        assertEquals("Hello", BasicOperation.removeWhiteSpace("H e llo"));
        assertEquals("Hello", BasicOperation.removeWhiteSpace("   H e llo   "));
        assertEquals("Hello", BasicOperation.removeWhiteSpace("\n \n H \te llo \n \n  "));
        assertEquals("Hello", BasicOperation.removeWhiteSpace(" H   e   l\nl \t o"));
    }

    @Test
    public void givenAList_whenJoingIsCalled_thenWeCreateAString() {
        assertEquals("[1, 2, 3]", BasicOperation.fromCollection(Arrays.asList(1, 2, 3)));
        assertEquals("[a, b, c]", BasicOperation.fromCollection(Arrays.asList('a', 'b', 'c')));
        assertEquals("[]", BasicOperation.fromCollection(Collections.emptySet()));
    }

    @Test
    public void givenAStringWithPipesAsSeperator_whenSpitByRegExPipeIsCalled_thenWeGetAnArrayWithTheSeperatedStrings() {
        final String[] expected = { "Hello", "baeldung", "!" };
        assertArrayEquals(expected, BasicOperation.splitByRegExPipe("Hello|baeldung|!"));
    }

    @Test
    public void givenAStringWithPipesAsSeperator_whenSpitByPipeIsCalled_thenWeGetAnArrayWithTheSeperatedStrings() {
        final String[] expected = { "Hello", "baeldung", "!" };
        assertArrayEquals(expected, BasicOperation.splitByPatternPipe("Hello|baeldung|!"));
    }

    @Test
    public void givenAChar_whenWeCastToIntValue_thenWeGetTheASCIINumber() {
        assertEquals(100, BasicOperation.asciiValue('d'));
        assertEquals(123, BasicOperation.asciiValue('{'));
    }

    @Test
    public void givenAnAsciiValue_whenWeCastToChar_thenWeGetTheCharValue() {
        assertEquals('d', BasicOperation.fromAsciiValue(100));
        assertEquals('{', BasicOperation.fromAsciiValue(123));
    }

    @Test
    public void whenUsingEquals_thenWeCheckForTheSameValue() {
        assertTrue("Values are equal", new String("Test").equals("Test"));
    }

    @Test
    public void whenUsingEqualsSign_thenWeCheckForReferenceEquality() {
        assertFalse("References are not equal", new String("Test") == "Test");
    }

    @Test
    public void whenTheCompileCanBuildUpAString_thenWeGetTheSameReference() {
        assertTrue("Literals are concated by the compiler", "Test" == "Te"+"st");
    }

    @Test
    public void whenUsingIsEmpty_thenWeCheckForNullorLengthZero() {
        assertTrue("null is empty", isEmpty(null));
        assertTrue("nothing is empty", isEmpty(""));
        assertFalse("whitespace is not empty", isEmpty(" "));
        assertFalse("whitespace is not empty", isEmpty("\n"));
        assertFalse("whitespace is not empty", isEmpty("\t"));
        assertFalse("text is not empty", isEmpty("Anything!"));
    }

    @Test
    public void whenUsingIsBlank_thenWeCheckForNullorOnlyContainingWhitespace() {
        assertTrue("null is blank", isBlank(null));
        assertTrue("nothing is blank", isBlank(""));
        assertTrue("whitespace is blank", isBlank("\t\t \t\n\r"));
        assertFalse("test is not blank", isBlank("Anything!"));
    }
}