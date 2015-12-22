package org.baeldung.mockito;

import java.util.List;

import org.easymock.IArgumentMatcher;

public class CustomEasyMockMatcher implements IArgumentMatcher {

	public boolean matches(Object argument) {
		return ((List) argument).size() == 2;
	}

	public void appendTo(StringBuffer buffer) {
		
	}
}
