package com.baeldung.typechecker;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class NonNullExample {

    private int countArgs(@NonNull String[] args) {
        return args.length;
    }

    public String status(@Nullable String[] args) {
        return "There are " + countArgs(args) + " items";
    }

}
