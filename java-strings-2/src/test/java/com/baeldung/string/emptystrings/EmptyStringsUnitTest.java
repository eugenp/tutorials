package com.baeldung.string.emptystrings;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import joptsimple.internal.Strings;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class EmptyStringsUnitTest {

    private String emptyString = "";
    private String blankString = "  \n\t  ";
    private String nonEmptyString = "   someString   ";

    @Test
    public void checkForEmptyString() {
        assertTrue(new EmptyStringCheck().isEmptyString(emptyString));
        assertFalse(new EmptyStringCheck().isEmptyString(nonEmptyString));
        assertFalse(new EmptyStringCheck().isEmptyString(blankString));
    }

    @Test
    public void checkForEmptyStringJava5() {
        assertTrue(new Java5EmptyStringCheck().isEmptyString(emptyString));
        assertFalse(new Java5EmptyStringCheck().isEmptyString(nonEmptyString));
        assertFalse(new Java5EmptyStringCheck().isEmptyString(blankString));
    }

    @Test
    public void checkForBlankString() {
        assertTrue(new PlainJavaBlankStringCheck().isBlankString(emptyString));
        assertFalse(new PlainJavaBlankStringCheck().isBlankString(nonEmptyString));
        assertTrue(new PlainJavaBlankStringCheck().isBlankString(blankString));
    }

    @Test
    public void checkForBlankStringWithApacheCommonsStringUtils() {
        assertTrue(StringUtils.isBlank(emptyString));
        assertFalse(StringUtils.isBlank(nonEmptyString));
        assertTrue(StringUtils.isBlank(blankString));
    }

    @Test
    public void detectBlankStringsWithGoogleGuava() {
        assertTrue(Strings.isNullOrEmpty(emptyString));
        assertFalse(Strings.isNullOrEmpty(nonEmptyString));
        assertFalse(Strings.isNullOrEmpty(blankString));
    }

    @Test
    public void detectBlankStringsWithBeanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        SomeClassWithValidations someClassWithValidations;
        Set<ConstraintViolation<SomeClassWithValidations>> violations;

        someClassWithValidations = new SomeClassWithValidations().setSomeString(emptyString);
        violations = validator.validate(someClassWithValidations);
        assertThat(violations, iterableWithSize(1));

        someClassWithValidations = new SomeClassWithValidations().setSomeString(blankString);
        violations = validator.validate(someClassWithValidations);
        assertThat(violations, iterableWithSize(1));

        someClassWithValidations = new SomeClassWithValidations().setSomeString(nonEmptyString);
        violations = validator.validate(someClassWithValidations);
        assertThat(violations, iterableWithSize(0));
    }
}
