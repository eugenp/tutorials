package com.baeldung.hamcrest.custommatchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsDivisibleBy extends TypeSafeMatcher<Integer> {

    private Integer divider;

    private IsDivisibleBy(Integer divider) {
        this.divider = divider;
    }

    @Override
    protected boolean matchesSafely(Integer dividend) {
        if (divider == 0) return false;
        return ((dividend % divider) == 0);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("divisible by " + divider);
    }

    public static Matcher<Integer> divisibleBy(Integer divider) {
        return new IsDivisibleBy(divider);
    }
}
