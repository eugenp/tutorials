package com.baeldung.cglib.mixin;

public class Class1 implements Interface1 {
    @Override
    public String first() {
        return "first behaviour";
    }
}