package com.baeldung.passay;

import org.junit.Assert;
import org.junit.Test;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.stream.Stream;

public class PasswordGeneratorUnitTest {

    @Test
    public void givenDigitsGenerator_whenGeneratingPassword_thenPasswordContainsDigitsHasLength10() {
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(10, digits);

        Assert.assertTrue(password.length() == 10);
        Assert.assertTrue(containsOnlyCharactersFromSet(password, "0123456789"));
    }

    @Test
    public void givenCustomizedRule_whenGenerating_thenGeneratedPasswordContainsCustomizedCharacters() {
        CharacterRule specialCharacterRule = new CharacterRule(new CharacterData() {
            @Override
            public String getErrorCode() {
                return "SAMPLE_ERROR_CODE";
            }

            @Override
            public String getCharacters() {
                return "ABCxyz123!@#";
            }
        });

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(10, specialCharacterRule);

        Assert.assertTrue(containsOnlyCharactersFromSet(password, "ABCxyz123!@#"));
    }

    private boolean containsOnlyCharactersFromSet(String password, String setOfCharacters) {
        return Stream.of(password.split(""))
            .allMatch(it -> setOfCharacters.contains(it));
    }

}
