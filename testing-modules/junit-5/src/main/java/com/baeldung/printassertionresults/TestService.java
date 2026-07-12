package com.baeldung.printassertionresults;

public class TestService {
    public boolean successfulCall() {
        return true;
    }

    public boolean failedCall() {
        return false;
    }

    public boolean exceptionCall() {
        throw new RuntimeException("Service error");
    }
}
