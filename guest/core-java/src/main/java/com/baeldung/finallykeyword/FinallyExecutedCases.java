package com.baeldung.finallykeyword;

public class FinallyExecutedCases {

    public void noExceptionFinally() {
        try {
            System.out.println("Inside try");
        } finally {
            System.out.println("Inside finally");
        }
    }

    public void unhandledException() throws Exception {
        try {
            System.out.println("Inside try");
            throw new Exception();
        } finally {
            System.out.println("Inside finally");
        }
    }

    public void handledException() {
        try {
            System.out.println("Inside try");
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Inside catch");
        } finally {
            System.out.println("Inside finally");
        }
    }

    public String returnFromTryOrCatch() {
        try {
            System.out.println("Inside try");
            return "from try";
        } catch (Exception e) {
            System.out.println("Inside catch");
            return "from catch";
        } finally {
            System.out.println("Inside finally");
        }
    }
}
