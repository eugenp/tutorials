package com.baeldung.removenonalpahabeticchars;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StringManipulatorUnitTest {

    @Test
    public void givenMixedString_whenRemoveNonAlphabeticUsingRegex_thenReturnsOnlyAlphabeticCharacters() {
        String[] inputArray = { "Hello123", "Java@Code", "Stack#Overflow" };
        String[] expectedArray = { "Hello", "JavaCode", "StackOverflow" };
        for (int i = 0; i < inputArray.length; i++) {
            assertEquals(expectedArray[i], StringManipulator.removeNonAlphabeticUsingRegex(inputArray[i]));
        }
    }

    @Test
    public void givenMixedString_whenRemoveNonAlphabeticCharactersUsingStreams_thenReturnsOnlyAlphabeticCharacters() {
        String[] inputArray = { "Stream123", "Functional!", "Lambda$Syntax" };
        String[] expectedArray = { "Stream", "Functional", "LambdaSyntax" };
        for (int i = 0; i < inputArray.length; i++) {
            assertEquals(expectedArray[i], StringManipulator.removeNonAlphabeticUsingStreams(inputArray[i]));
        }
    }

    @Test
    public void givenMixedString_whenRemoveNonAlphabeticCharactersUsingStringBuilder_thenReturnsOnlyAlphabeticCharacters() {
        String[] inputArray = { "Builder@Example", "Remove123Chars", "Efficient*Code" };
        String[] expectedArray = { "BuilderExample", "RemoveChars", "EfficientCode" };
        for (int i = 0; i < inputArray.length; i++) {
            assertEquals(expectedArray[i], StringManipulator.removeNonAlphabeticUsingStringBuilder(inputArray[i]));
        }
    }

    @Test
    public void givenMixedString_whenRemoveNonAlphabeticCharactersUsingpacheCommons_thenReturnsOnlyAlphabeticCharacters() {
        String[] inputArray = { "Commons123Lang", "Apache@Utility", "String#Utils" };
        String[] expectedArray = { "CommonsLang", "ApacheUtility", "StringUtils" };
        for (int i = 0; i < inputArray.length; i++) {
            assertEquals(expectedArray[i], StringManipulator.removeNonAlphabeticWithApacheCommons(inputArray[i]));
        }
    }
}
