package com.baeldung.finallykeyword;

public class PitfallsWhenUsingFinally {

    public String disregardsUnCaughtException() {
        try {
            System.out.println("Inside try");
            throw new RuntimeException();
        } finally {
            System.out.println("Inside finally");
            return "from finally";
        }
    }

    public String ignoringOtherReturns() {
        try {
            System.out.println("Inside try");
            return "from try";
        } finally {
            System.out.println("Inside finally");
            return "from finally";
        }
    }

    public String throwsException() {
        try {
            System.out.println("Inside try");
            return "from try";
        } finally {
            throw new RuntimeException();
        }
    }
}
