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
    public void whenMatchesTenDigitsNumberWhitespacesDotHyphen_thenCorrect() {
        Pattern pattern = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
        Matcher matcher = pattern.matcher("202 555 0125");
        assertTrue(matcher.matches());
    }

    @Test
    public void whenMatchesTenDigitsNumberParenthesis_thenCorrect() {
        Pattern pattern = Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("(202) 555-0125");
        assertTrue(matcher.matches());
    }

    @Test
    public void whenMatchesTenDigitsNumberPrefix_thenCorrect() {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("+111 (202) 555-0125");
        assertTrue(matcher.matches());
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
}
