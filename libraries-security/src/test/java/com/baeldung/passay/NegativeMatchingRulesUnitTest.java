package com.baeldung.passay;

import org.cryptacular.bean.EncodingHashBean;
import org.cryptacular.spec.CodecSpec;
import org.cryptacular.spec.DigestSpec;
import org.junit.Assert;
import org.junit.Test;
import org.passay.DictionaryRule;
import org.passay.DictionarySubstringRule;
import org.passay.DigestHistoryRule;
import org.passay.EnglishSequenceData;
import org.passay.HistoryRule;
import org.passay.IllegalCharacterRule;
import org.passay.IllegalRegexRule;
import org.passay.IllegalSequenceRule;
import org.passay.NumberRangeRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RepeatCharacterRegexRule;
import org.passay.RuleResult;
import org.passay.SourceRule;
import org.passay.UsernameRule;
import org.passay.WhitespaceRule;
import org.passay.dictionary.ArrayWordList;
import org.passay.dictionary.WordListDictionary;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class NegativeMatchingRulesUnitTest {

    @Test
    public void givenDictionaryRules_whenValidatePassword_thenFoundIllegalWordsFromDictionary() {
        ArrayWordList arrayWordList = new ArrayWordList(new String[] { "bar", "foobar" });

        WordListDictionary wordListDictionary = new WordListDictionary(arrayWordList);

        DictionaryRule dictionaryRule = new DictionaryRule(wordListDictionary);
        DictionarySubstringRule dictionarySubstringRule = new DictionarySubstringRule(wordListDictionary);

        PasswordValidator passwordValidator = new PasswordValidator(dictionaryRule, dictionarySubstringRule);
        RuleResult validate = passwordValidator.validate(new PasswordData("foobar"));

        assertFalse(validate.isValid());
        assertEquals("ILLEGAL_WORD:{matchingWord=foobar}", getDetail(validate, 0));
        assertEquals("ILLEGAL_WORD:{matchingWord=bar}", getDetail(validate, 1));
    }

    @Test
    public void givenHistoryRule_whenValidatePassword_thenFoundIllegalWordsFromHistory() {
        HistoryRule historyRule = new HistoryRule();

        PasswordData passwordData = new PasswordData("123");
        passwordData.setPasswordReferences(new PasswordData.HistoricalReference("12345"), new PasswordData.HistoricalReference("1234"), new PasswordData.HistoricalReference("123"));

        PasswordValidator passwordValidator = new PasswordValidator(historyRule);

        RuleResult validate = passwordValidator.validate(passwordData);

        assertFalse(validate.isValid());
        assertEquals("HISTORY_VIOLATION:{historySize=3}", getDetail(validate, 0));
    }

    @Test
    public void givenSeveralIllegalRules_whenValidatePassword_thenFoundSeveralIllegalPatterns() {
        IllegalCharacterRule illegalCharacterRule = new IllegalCharacterRule(new char[] { 'a' });
        IllegalRegexRule illegalRegexRule = new IllegalRegexRule("\\w{2}\\d{2}");
        IllegalSequenceRule illegalSequenceRule = new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, true);
        NumberRangeRule numberRangeRule = new NumberRangeRule(1, 10);
        WhitespaceRule whitespaceRule = new WhitespaceRule();

        PasswordValidator passwordValidator = new PasswordValidator(illegalCharacterRule, illegalRegexRule, illegalSequenceRule, numberRangeRule, whitespaceRule);

        RuleResult validate = passwordValidator.validate(new PasswordData("abcd22 "));

        assertFalse(validate.isValid());
        assertEquals("ILLEGAL_CHAR:{illegalCharacter=a, matchBehavior=contains}", getDetail(validate, 0));
        assertEquals("ILLEGAL_MATCH:{match=cd22, pattern=\\w{2}\\d{2}}", getDetail(validate, 1));
        assertEquals("ILLEGAL_ALPHABETICAL_SEQUENCE:{sequence=abc}", getDetail(validate, 2));
        assertEquals("ILLEGAL_ALPHABETICAL_SEQUENCE:{sequence=bcd}", getDetail(validate, 3));
        assertEquals("ILLEGAL_NUMBER_RANGE:{number=2, matchBehavior=contains}", getDetail(validate, 4));
        assertEquals("ILLEGAL_WHITESPACE:{whitespaceCharacter= , matchBehavior=contains}", getDetail(validate, 5));
    }

    @Test
    public void givenSourceRule_whenValidatePassword_thenFoundIllegalWordsFromSource() {
        SourceRule sourceRule = new SourceRule();

        PasswordData passwordData = new PasswordData("password");
        passwordData.setPasswordReferences(new PasswordData.SourceReference("source", "password"));

        PasswordValidator passwordValidator = new PasswordValidator(sourceRule);
        RuleResult validate = passwordValidator.validate(passwordData);

        assertFalse(validate.isValid());
        assertEquals("SOURCE_VIOLATION:{source=source}", getDetail(validate, 0));
    }

    @Test
    public void givenRepeatCharacterRegexRuleRule_whenValidatePassword_thenFoundIllegalPatternMatches() {
        RepeatCharacterRegexRule repeatCharacterRegexRule = new RepeatCharacterRegexRule(3);

        PasswordValidator passwordValidator = new PasswordValidator(repeatCharacterRegexRule);

        RuleResult validate = passwordValidator.validate(new PasswordData("aaabbb"));

        assertFalse(validate.isValid());
        assertEquals("ILLEGAL_MATCH:{match=aaa, pattern=([^\\x00-\\x1F])\\1{2}}", getDetail(validate, 0));
        assertEquals("ILLEGAL_MATCH:{match=bbb, pattern=([^\\x00-\\x1F])\\1{2}}", getDetail(validate, 1));
    }

    @Test
    public void givenUserNameRule_whenValidatePassword_thenFoundUserNameInPassword() {
        PasswordValidator passwordValidator = new PasswordValidator(new UsernameRule());

        PasswordData passwordData = new PasswordData("testuser1234");
        passwordData.setUsername("testuser");

        RuleResult validate = passwordValidator.validate(passwordData);

        assertFalse(validate.isValid());
        assertEquals("ILLEGAL_USERNAME:{username=testuser, matchBehavior=contains}", getDetail(validate, 0));
    }

    @Test
    public void givenPasswordAndHashBeanAndEncryptedReferences_whenValidate_thenPasswordValidationShouldPass() {
        List<PasswordData.Reference> historicalReferences = Arrays.asList(new PasswordData.HistoricalReference("SHA256", "2e4551de804e27aacf20f9df5be3e8cd384ed64488b21ab079fb58e8c90068ab"));
        PasswordData passwordData = new PasswordData("example!");
        passwordData.setPasswordReferences(historicalReferences);

        EncodingHashBean encodingHashBean = new EncodingHashBean(new CodecSpec("Base64"), new DigestSpec("SHA256"), 1, false);

        PasswordValidator passwordValidator = new PasswordValidator(new DigestHistoryRule(encodingHashBean));

        RuleResult validate = passwordValidator.validate(passwordData);

        Assert.assertTrue(validate.isValid());
    }

    private String getDetail(RuleResult validate, int i) {
        return validate.getDetails()
            .get(i)
            .toString();
    }

}
