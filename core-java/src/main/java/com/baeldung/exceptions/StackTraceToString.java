package com.baeldung.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceToString {

    public static void main(String[] args) {
        // Convert a StackTrace to String using core java
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            System.out.println(sw.toString());
        }

        // Convert a StackTrace to String using Apache Commons
        try {
            throw new IndexOutOfBoundsException();
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }

}
