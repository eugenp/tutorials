package com.baeldung.emptystrings;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.ObjectUtils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.*;

public class EmptyStringsUnitTest {

    private String emptyString = "";
    private String blankString = "  \n\t  ";
    private String nonEmptyString = "   someString   ";

    /*
     * EmptyStringCheck
     */
    @Test
    public void givenSomeEmptyString_thenEmptyStringCheckIsEmptyStringReturnsTrue() {
        assertTrue(new EmptyStringCheck().isEmptyString(emptyString));
    }

    @Test
    public void givenSomeNonEmptyString_thenEmptyStringCheckIsEmptyStringReturnsFalse() {
        assertFalse(new EmptyStringCheck().isEmptyString(nonEmptyString));
    }

    @Test
    public void givenSomeBlankString_thenEmptyStringCheckIsEmptyStringReturnsFalse() {
        assertFalse(new EmptyStringCheck().isEmptyString(blankString));
    }

    /*
     * Java5EmptyStringCheck
     */
    @Test
    public void givenSomeEmptyString_thenJava5EmptyStringCheckIsEmptyStringReturnsTrue() {
        assertTrue(new Java5EmptyStringCheck().isEmptyString(emptyString));
    }

    @Test
    public void givenSomeNonEmptyString_thenJava5EmptyStringCheckIsEmptyStringReturnsFalse() {
        assertFalse(new Java5EmptyStringCheck().isEmptyString(nonEmptyString));
    }

    @Test
    public void givenSomeBlankString_thenJava5EmptyStringCheckIsEmptyStringReturnsFalse() {
        assertFalse(new Java5EmptyStringCheck().isEmptyString(blankString));
    }

    /*
     * PlainJavaBlankStringCheck
     */
    @Test
    public void givenSomeEmptyString_thenPlainJavaBlankStringCheckIsBlankStringReturnsTrue() {
        assertTrue(new PlainJavaBlankStringCheck().isBlankString(emptyString));
    }

    @Test
    public void givenSomeNonEmptyString_thenPlainJavaBlankStringCheckIsBlankStringReturnsFalse() {
        assertFalse(new PlainJavaBlankStringCheck().isBlankString(nonEmptyString));
    }

    @Test
    public void givenSomeBlankString_thenPlainJavaBlankStringCheckIsBlankStringReturnsTrue() {
        assertTrue(new PlainJavaBlankStringCheck().isBlankString(blankString));
    }

    /*
     * Apache Commons Lang StringUtils
     */
    @Test
    public void givenSomeEmptyString_thenStringUtilsIsBlankReturnsTrue() {
        assertTrue(StringUtils.isBlank(emptyString));
    }

    @Test
    public void givenSomeNonEmptyString_thenStringUtilsIsBlankReturnsFalse() {
        assertFalse(StringUtils.isBlank(nonEmptyString));
    }

    @Test
    public void givenSomeBlankString_thenStringUtilsIsBlankReturnsTrue() {
        assertTrue(StringUtils.isBlank(blankString));
    }

    /*
     * Google Guava Strings
     */
    @Test
    public void givenSomeEmptyString_thenStringsIsNullOrEmptyStringReturnsTrue() {
        assertTrue(Strings.isNullOrEmpty(emptyString));
    }

    @Test
    public void givenSomeNonEmptyString_thenStringsIsNullOrEmptyStringReturnsFalse() {
        assertFalse(Strings.isNullOrEmpty(nonEmptyString));
    }

    @Test
    public void givenSomeBlankString_thenStringsIsNullOrEmptyStringReturnsFalse() {
        assertFalse(Strings.isNullOrEmpty(blankString));
    }

    /*
     * Spring Core ObjectUtils
     */
    @Test
    public void givenSomeEmptyString_thenObjectUtilsIsEmptyReturnsTrue() {
        assertTrue(ObjectUtils.isEmpty(emptyString));
    }

    @Test
    public void givenSomeNonEmptyString_thenObjectUtilsIsEmptyReturnsFalse() {
        assertFalse(ObjectUtils.isEmpty(nonEmptyString));
    }

    @Test
    public void givenSomeBlankString_thenObjectUtilsIsEmptyReturnsFalse() {
        assertFalse(ObjectUtils.isEmpty(blankString));
    }

    /*
     * Bean Validation
     */
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    @Test
    public void givenSomeEmptyString_thenBeanValidationReturnsViolations() {
        SomeClassWithValidations someClassWithValidations = new SomeClassWithValidations().setSomeString(emptyString);
        Set<ConstraintViolation<SomeClassWithValidations>> violations = validator.validate(someClassWithValidations);
        assertThat(violations, iterableWithSize(1));
    }

    @Test
    public void givenSomeNonEmptyString_thenBeanValidationValidatesWithoutViolations() {
        SomeClassWithValidations someClassWithValidations = new SomeClassWithValidations().setSomeString(nonEmptyString);
        Set<ConstraintViolation<SomeClassWithValidations>> violations = validator.validate(someClassWithValidations);
        assertThat(violations, iterableWithSize(0));
    }

    @Test
    public void givenSomeBlankString_thenBeanValidationReturnsViolations() {
        SomeClassWithValidations someClassWithValidations = new SomeClassWithValidations().setSomeString(blankString);
        Set<ConstraintViolation<SomeClassWithValidations>> violations = validator.validate(someClassWithValidations);
        assertThat(violations, iterableWithSize(1));
    }
}
