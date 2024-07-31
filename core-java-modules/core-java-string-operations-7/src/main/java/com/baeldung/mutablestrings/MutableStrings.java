package com.baeldung.mutablestrings;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import com.google.errorprone.annotations.DoNotCall;

public class MutableStrings {

    /**
     * This involves using Reflection to change String fields and it is not encouraged to use this in programs.
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @DoNotCall
    public void mutableUsingReflection() throws NoSuchFieldException, IllegalAccessException {
        String myString = "Hello World";
        String otherString = new String("Hello World");
        Field f = String.class.getDeclaredField("value");
        f.setAccessible(true);
        f.set(myString, "Hi World".toCharArray());
        System.out.println(otherString);
    }
}
