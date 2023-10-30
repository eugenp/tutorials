package com.baeldung.unicode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class UnicodeLetterChecker {
    public boolean characterClassCheck(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean regexCheck(String input) {
        Pattern pattern = Pattern.compile("^\\p{L}+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    
    public boolean isAlphaCheck(String input) {
        return StringUtils.isAlpha(input);
    }
    
    public boolean StreamsCheck(String input) {
        return input.codePoints().allMatch(Character::isLetter);
    }
}
