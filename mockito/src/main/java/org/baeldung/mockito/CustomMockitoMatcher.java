package org.baeldung.mockito;

import java.util.List;

import org.hamcrest.Description;
import org.mockito.ArgumentMatcher;

public class CustomMockitoMatcher extends ArgumentMatcher<List> {

	@Override
	public boolean matches(final Object argument) {
		return ((List) argument).size() == 2;
	}

	@Override
	public void describeTo(final Description description) {
		//System.out.println("List with two elements");
	}
}
