package com.baeldung.emailvalidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class EmailValidation {

    public static boolean usingEmailValidator(String emailAddress) {

        EmailValidator validator = EmailValidator.getInstance();

        return validator.isValid(emailAddress);
    }

    public static boolean usingSimpleRegEx(String emailAddress) {

        String regExPattern = "^(.+)@(\\S+)$";

        Pattern pattern = Pattern.compile(regExPattern);

        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.matches();

    }

    public static boolean usingStrictRegEx(String emailAddress) {

        String regExPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(regExPattern);

        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.matches();

    }

    public static boolean usingUnicodeRegEx(String emailAddress) {

        String regExPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@" 
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";

        Pattern pattern = Pattern.compile(regExPattern);

        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.matches();

    }

    public static boolean usingRFC5322RegEx(String emailAddress) {

        String regExPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regExPattern);

        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.matches();

    }

    public static boolean restrictDots(String emailAddress) {

        String regExPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@" 
            + "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        Pattern pattern = Pattern.compile(regExPattern);

        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.matches();

    }

    public static boolean owaspValidation(String emailAddress) {

        String regExPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(regExPattern);

        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.matches();

    }

    public static boolean topLevelDomain(String emailAddress) {

        String regExPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" 
        + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regExPattern);

        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.matches();

    }

}
