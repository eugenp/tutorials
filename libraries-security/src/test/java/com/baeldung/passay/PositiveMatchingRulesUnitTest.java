package com.baeldung.passay;

import org.junit.Test;
import org.passay.AllowedCharacterRule;
import org.passay.AllowedRegexRule;
import org.passay.CharacterCharacteristicsRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthComplexityRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

import static org.junit.Assert.*;

public class PositiveMatchingRulesUnitTest {

    @Test
    public void givenPasswordValidationRules_whenValidatingPassword_thenPosswordIsNotValidWithSeveralErrors() {
        PasswordValidator passwordValidator = new PasswordValidator(new AllowedCharacterRule(new char[] { 'a', 'b', 'c' }), new AllowedRegexRule("\\d{2}\\w{10}"), new CharacterRule(EnglishCharacterData.LowerCase, 5), new LengthRule(8, 10));

        RuleResult validate = passwordValidator.validate(new PasswordData("12abc"));

        assertFalse(validate.isValid());
        assertEquals("ALLOWED_CHAR:{illegalCharacter=1, matchBehavior=contains}", getDetail(validate, 0));
        assertEquals("ALLOWED_CHAR:{illegalCharacter=2, matchBehavior=contains}", getDetail(validate, 1));
        assertEquals("ALLOWED_MATCH:{pattern=\\d{2}\\w{10}}", getDetail(validate, 2));
        assertEquals("INSUFFICIENT_LOWERCASE:{" + "minimumRequired=5, matchingCharacterCount=3, " + "validCharacters=abcdefghijklmnopqrstuvwxyz, " + "matchingCharacters=abc}", getDetail(validate, 3));
        assertEquals("TOO_SHORT:{minimumLength=8, maximumLength=10}", getDetail(validate, 4));
    }

    @Test
    public void givenRulesForDifferentPasswordLength_whenValidatingTwoDifferentPassword_thenBothOfThemAreInvalid() {
        PasswordData shortPassword = new PasswordData("12ab");
        PasswordData longPassword = new PasswordData("1234abcde");

        LengthComplexityRule lengthComplexityRule = new LengthComplexityRule();
        lengthComplexityRule.addRules("[1,5]", new CharacterRule(EnglishCharacterData.LowerCase, 5));
        lengthComplexityRule.addRules("[6,10]", new AllowedCharacterRule(new char[] { 'a', 'b', 'c', 'd' }));

        PasswordValidator passwordValidator = new PasswordValidator(lengthComplexityRule);

        RuleResult validateShort = passwordValidator.validate(shortPassword);
        RuleResult validateLong = passwordValidator.validate(longPassword);

        assertFalse(validateShort.isValid());
        assertFalse(validateLong.isValid());

        assertEquals("INSUFFICIENT_LOWERCASE:{" + "minimumRequired=5, " + "matchingCharacterCount=2, " + "validCharacters=abcdefghijklmnopqrstuvwxyz, " + "matchingCharacters=ab}", getDetail(validateShort, 0));
        assertEquals("ALLOWED_CHAR:{illegalCharacter=1, matchBehavior=contains}", getDetail(validateLong, 0));
    }

    @Test
    public void givenCharacterCharacteristicsRule_whenValidatingPassword_thenItIsInvalidAsItBreaksToManyRules() {
        PasswordData shortPassword = new PasswordData();
        shortPassword.setPassword("12345abcde!");

        CharacterCharacteristicsRule characterCharacteristicsRule = new CharacterCharacteristicsRule(4, new CharacterRule(EnglishCharacterData.LowerCase, 5), new CharacterRule(EnglishCharacterData.UpperCase, 5), new CharacterRule(EnglishCharacterData.Digit),
            new CharacterRule(EnglishCharacterData.Special));

        PasswordValidator passwordValidator = new PasswordValidator(characterCharacteristicsRule);

        RuleResult validate = passwordValidator.validate(shortPassword);
        assertFalse(validate.isValid());

        assertEquals("INSUFFICIENT_UPPERCASE:{" + "minimumRequired=5, " + "matchingCharacterCount=0, " + "validCharacters=ABCDEFGHIJKLMNOPQRSTUVWXYZ, " + "matchingCharacters=}", getDetail(validate, 0));
        assertEquals("INSUFFICIENT_CHARACTERISTICS:{" + "successCount=3, " + "minimumRequired=4, " + "ruleCount=4}", getDetail(validate, 1));
    }

    private String getDetail(RuleResult validate, int i) {
        return validate.getDetails()
            .get(i)
            .toString();
    }
}
