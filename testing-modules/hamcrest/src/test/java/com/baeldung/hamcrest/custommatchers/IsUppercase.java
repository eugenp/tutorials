package com.baeldung.hamcrest.custommatchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsUppercase extends TypeSafeMatcher<String> {

    @Override
    protected boolean matchesSafely(String s) {
        return s.equals(s.toUpperCase());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("all uppercase");
    }

    public static Matcher<String> uppercase() {
        return new IsUppercase();
    }
}