package com.baeldung;

@FunctionalInterface
public interface Foo {

    String method(String string);
	//String method(String string1, String string2 );

    default void defaultMethod() {
    }
}
