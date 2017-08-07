package com.baeldung.noexception;

import com.machinezoo.noexception.Exceptions;

public class NoExceptionHandling {

    public static void main(String[] args) {

        Exceptions.log()
            .run(() -> System.out.println("Result is " + Integer.parseInt("foobar")));

    }

}
