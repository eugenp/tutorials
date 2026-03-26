package com.baeldung.printassertionresults;

public class AssertionWithMessage {
    private final Assertion assertion;
    private final String message;

    public AssertionWithMessage(Assertion assertion, String message) {
        this.assertion = assertion;
        this.message = message;
    }

    public void doAssert() {
        assertion.doAssert();
    }

    public String getMessage() {
        return message;
    }
}
