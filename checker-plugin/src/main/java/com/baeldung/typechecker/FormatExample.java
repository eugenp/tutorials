package com.baeldung.typechecker;

public class FormatExample {

    public static void main(String[] args) {
        String.format("%y", 7);           // error: invalid format string

        String.format("%d", "a string");  // error: invalid argument type for %d

        String.format("%d %s", 7);        // error: missing argument for %s
        String.format("%d", 7, 3);        // warning: unused argument 3
        String.format("{0}", 7);          // warning: unused argument 7, because {0} is wrong syntax
    }

}
