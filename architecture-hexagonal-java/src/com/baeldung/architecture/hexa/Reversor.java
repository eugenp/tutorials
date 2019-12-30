package com.baeldung.architecture.hexa;

public class Reversor implements ReversorPort{
    @Override
    public String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}