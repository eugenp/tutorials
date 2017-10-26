package com.baeldung.ditypes;

public class CreditTransaction implements Transaction{

    @Override
    public String process() {
        return "creditTransaction";
    }
}
