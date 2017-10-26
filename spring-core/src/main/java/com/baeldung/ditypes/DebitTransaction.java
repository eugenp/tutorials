package com.baeldung.ditypes;

public class DebitTransaction implements Transaction{

    @Override
    public String process() {
        return "Debit Transaction Processed";
    }
}
