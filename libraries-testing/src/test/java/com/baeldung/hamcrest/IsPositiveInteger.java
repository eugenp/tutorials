package com.baeldung.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IsPositiveInteger extends TypeSafeMatcher<Integer> {

	public void describeTo(Description description) {
		description.appendText("a positive integer");
	}

	@Factory
	public static Matcher<Integer> isAPositiveInteger() {
		return new IsPositiveInteger();
	}

	@Override
	protected boolean matchesSafely(Integer integer) {
		return integer > 0;
	}

}
