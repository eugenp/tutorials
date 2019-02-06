package com.baeldung.examples.guice;

import org.springframework.lang.Nullable;

import com.google.inject.Inject;

public class FooProcessor {

	@Inject
	@Nullable
	private Foo foo;
}