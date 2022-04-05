package com.baeldung.passay;

import org.junit.Assert;
import org.junit.Test;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.passay.RuleResultMetadata;
import org.passay.WhitespaceRule;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PasswordValidatorUnitTest {

    @Test
    public void givenPasswordValidatorWithLengthRule_whenValidation_thenTooShortPassword() {
        PasswordData passwordData = new PasswordData("1234");

        PasswordValidator passwordValidator = new PasswordValidator(new LengthRule(5));

        RuleResult validate = passwordValidator.validate(passwordData);
        assertEquals(false, validate.isValid());

        RuleResultDetail ruleResultDetail = validate.getDetails()
            .get(0);
        assertEquals("TOO_SHORT", ruleResultDetail.getErrorCode());
        assertEquals(5, ruleResultDetail.getParameters()
            .get("minimumLength"));
        assertEquals(5, ruleResultDetail.getParameters()
            .get("maximumLength"));

        Integer lengthCount = validate.getMetadata()
            .getCounts()
            .get(RuleResultMetadata.CountCategory.Length);
        assertEquals(Integer.valueOf(4), lengthCount);
    }

    @Test
    public void givenPasswordValidatorWithLengthRule_whenValidation_thenTooLongPassword() {
        PasswordData passwordData = new PasswordData("123456");

        PasswordValidator passwordValidator = new PasswordValidator(new LengthRule(5));

        RuleResult validate = passwordValidator.validate(passwordData);
        assertFalse(validate.isValid());
        Assert.assertEquals("TOO_LONG", validate.getDetails()
            .get(0)
            .getErrorCode());
    }

    @Test
    public void givenPasswordValidatorWithLengthRule_whenValidation_thenCustomizedMeesagesAvailable() throws IOException {
        URL resource = this.getClass()
            .getClassLoader()
            .getResource("messages.properties");
        Properties props = new Properties();
        props.load(new FileInputStream(resource.getPath()));

        MessageResolver resolver = new PropertiesMessageResolver(props);
        PasswordValidator validator = new PasswordValidator(resolver, new LengthRule(8, 16), new WhitespaceRule());

        RuleResult tooShort = validator.validate(new PasswordData("XXXX"));
        RuleResult tooLong = validator.validate(new PasswordData("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ"));

        assertEquals("Password must not contain less characters than 16.", validator.getMessages(tooShort)
            .get(0));
        assertEquals("Password must not have more characters than 16.", validator.getMessages(tooLong)
            .get(0));
    }

}
