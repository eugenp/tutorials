package com.baeldung.removenonalphabets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StringManipluatorUnitTest {

    @Test
    public void givenStringWithSomeNonAlphabeticCharacters_whenRemoveNonAlphabeticUsingRegex_thenOnlyAlphabeticRemains() {
        String[] inputArray = { "Hello123", "Java@Code", "Stack#Overflow" };
        String[] expectedArray = { "Hello", "JavaCode", "StackOverflow" };
        for (int i = 0; i < inputArray.length; i++) {
            assertEquals(expectedArray[i], StringManipluator.removeNonAlphabeticUsingRegex(inputArray[i]));
        }
    }

    @Test
    public void givenStringWithSomeNonAlphabeticCharacters_whenRemoveNonAlphabeticUsingStreams_thenOnlyAlphabeticRemains() {
        String[] inputArray = { "Stream123", "Functional!", "Lambda$Syntax" };
        String[] expectedArray = { "Stream", "Functional", "LambdaSyntax" };
        for (int i = 0; i < inputArray.length; i++) {
            assertEquals(expectedArray[i], StringManipluator.removeNonAlphabeticUsingStreams(inputArray[i]));
        }
    }

    @Test
    public void givenStringWithSomeNonAlphabeticCharacters_whenRemoveNonAlphabeticUsingStringBuilder_thenOnlyAlphabeticRemains() {
        String[] inputArray = { "Builder@Example", "Remove123Chars", "Efficient*Code" };
        String[] expectedArray = { "BuilderExample", "RemoveChars", "EfficientCode" };
        for (int i = 0; i < inputArray.length; i++) {
            assertEquals(expectedArray[i], StringManipluator.removeNonAlphabeticUsingStringBuilder(inputArray[i]));
        }
    }

    @Test
    public void givenStringWithSomeNonAlphabeticCharacters_whenRemoveNonAlphabeticWithApacheCommons_thenOnlyAlphabeticRemains() {
        String[] inputArray = { "Commons123Lang", "Apache@Utility", "String#Utils" };
        String[] expectedArray = { "CommonsLang", "ApacheUtility", "StringUtils" };
        for (int i = 0; i < inputArray.length; i++) {
            assertEquals(expectedArray[i], StringManipluator.removeNonAlphabeticWithApacheCommons(inputArray[i]));
        }
    }
}

