package org.baeldung.hamcrest.custommatchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsOnlyNumbers extends TypeSafeMatcher<String> {

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
        description.appendText("only numbers");
    }

    public static Matcher<String> onlyNumbers() {
        return new IsOnlyNumbers();
    }
}