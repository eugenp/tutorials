package com.baeldung.nulls;

public class Preconditions {

    public void goodAccept(String one, String two, String three) {
        if (one == null || two == null || three == null) {
            throw new IllegalArgumentException();
        }

        process(one);
        process(two);
        process(three);
    }

    public void badAccept(String one, String two, String three) {
        if (one == null) {
            throw new IllegalArgumentException();
        } else {
            process(one);
        }

        if (two == null) {
            throw new IllegalArgumentException();
        } else {
            process(two);
        }

        if (three == null) {
            throw new IllegalArgumentException();
        } else {
            process(three);
        }

    }

    private void process(String one) {
    }

}
