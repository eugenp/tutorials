package com.baeldung.regex.phonenumbers;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class PhoneNumbersRegexUnitTest {

    @Test
    public void whenMatchesTenDigitsNumber_thenCorrect() {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher("2055550125");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenMOreThanTenDigits_thenNotCorrect() {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher("20555501251");
        assertFalse(matcher.matches());
    }

    @Test
    public void whenMatchesTenDigitsNumberWhitespacesDotHyphen_thenCorrect() {
        Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
        Matcher matcher = pattern.matcher("202 555 0125");
        assertTrue(matcher.matches());
    }
    
    @Test
    public void whenIncludesBracket_thenNotCorrect() {
        Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
        Matcher matcher = pattern.matcher("202]555 0125");
        assertFalse(matcher.matches());
    }
    
    @Test
    public void whenNotStartsWithBatchesOfThreeDigits_thenNotCorrect() {
        Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
        Matcher matcher = pattern.matcher("2021 555 0125");
        assertFalse(matcher.matches());
    }
    
    @Test
    public void whenLastPartWithNoFourDigits_thenNotCorrect() {
        Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
        Matcher matcher = pattern.matcher("202 555 012");
        assertFalse(matcher.matches());
    }

    @Test
    public void whenMatchesTenDigitsNumberParenthesis_thenCorrect() {
        Pattern pattern = Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("(202) 555-0125");
        assertTrue(matcher.matches());
    }

    @Test
    public void whenJustOpeningParenthesis_thenNotCorrect() {
        Pattern pattern = Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("(202 555-0125");
        assertFalse(matcher.matches());
    }

    @Test
    public void whenJustClosingParenthesis_thenNotCorrect() {
        Pattern pattern = Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("202) 555-0125");
        assertFalse(matcher.matches());
    }

    @Test
    public void whenMatchesTenDigitsNumberPrefix_thenCorrect() {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("+111 (202) 555-0125");
        assertTrue(matcher.matches());
    }

    @Test
    public void whenIncorrectPrefix_thenNotCorrect() {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("-111 (202) 555-0125");
        assertFalse(matcher.matches());
    }

    @Test
    public void whenTooLongPrefix_thenNotCorrect() {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("+1111 (202) 555-0125");
        assertFalse(matcher.matches());
    }

    @Test
    public void whenMatchesPhoneNumber_thenCorrect() {
        String patterns 
          = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
          + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
          + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

        String[] validPhoneNumbers 
          = {"2055550125","202 555 0125", "(202) 555-0125", "+111 (202) 555-0125", "636 856 789", "+111 636 856 789", "636 85 67 89", "+111 636 85 67 89"};

        Pattern pattern = Pattern.compile(patterns);
        for(String phoneNumber : validPhoneNumbers) {
            Matcher matcher = pattern.matcher(phoneNumber);
            assertTrue(matcher.matches());
        }
    }

    @Test
    public void whenNotMatchesPhoneNumber_thenNotCorrect() {
        String patterns 
          = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
          + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
          + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

        String[] invalidPhoneNumbers 
          = {"20555501251","202]555 0125", "2021 555 012", "(202 555-0125", "202) 555-0125", "-111 (202) 555-0125", "+1111 (202) 555-0125", "636 85 789", "636 85 67 893"};

        Pattern pattern = Pattern.compile(patterns);
        for(String phoneNumber : invalidPhoneNumbers) {
            Matcher matcher = pattern.matcher(phoneNumber);
            assertFalse(matcher.matches());
        }
    }
}
