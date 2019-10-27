package com.baeldung.hamcrest.custommatchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsOnlyDigits extends TypeSafeMatcher<String> {

    @Override
    protected boolean matchesSafely(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("only digits");
    }

    public static Matcher<String> onlyDigits() {
        return new IsOnlyDigits();
    }
}