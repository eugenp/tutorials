package com.baeldung.exception;

import java.io.FileNotFoundException;

public class ThrowsExample {

    /*
        Checked exceptions have Exception as a superclass. Some of these
        include IOException, NoSuchMethodException, and FileNotFoundException.
        If these are not declared or handled the code will compile.
     */
    class CheckedException extends Exception {
    }

    /*
        These are exceptions that do not require explicit handling however could
        be problematic if unhandled. Some of these include ArrayIndexOutOfBoundsException,
        NullPointerException, and NumberFormatException.
     */
    class UncheckedException extends RuntimeException {
    }

    class CompoundException extends Exception {
        public CompoundException(Exception ex) {
            super(ex);
        }
    }

    public void throwsCheckedExample1() throws FileNotFoundException {
        throw new FileNotFoundException();
    }

    /*
    public void throwsCheckedInvalidExample1() {
        throw new FileNotFoundException();
    }
    */

    public void throwsCheckedExample2() {
        try {
            throwsCheckedExample1();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void throwsCheckedExample3() throws CompoundException {
        try {
            throwsCheckedExample1();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            throw new CompoundException(ex);
        }
    }

    public void throwsUncheckedExample1() {
        throw new NullPointerException();
    }

    public static void main(String[] args) {
        ThrowsExample example = new ThrowsExample();

        // In this case the exception is handled within throwsCheckedExample2 so this is safe and compiles.
        example.throwsCheckedExample2();

        // In this case the exception is unchecked and the code will compile but upon execution will cause
        // an unrecoverable exception.
        example.throwsUncheckedExample1();

         /*
            Because this is an unhandled checked exception we must either handle it or
            declare it in the method signature. Otherwise there would be a compile failure.
         */
        try {
            example.throwsCheckedExample1();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*
            We can also catch more than one exception in a try block. This example also demonstrates
            'finally' which always will execute regardless of whether exceptions are thrown.
         */
        try {
            example.throwsCheckedExample1();
            example.throwsUncheckedExample1();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } finally {
            // Code within this block will always execute.
        }

        // Introduced in Java 7, we can more concisely handle exceptions.
        try {
            example.throwsCheckedExample1();
            example.throwsUncheckedExample1();
        } catch (FileNotFoundException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
