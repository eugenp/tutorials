package com.baeldung.cglib.mixin;

public class Class2 implements Interface2 {
    @Override
    public String second() {
        return "second behaviour";
    }
}