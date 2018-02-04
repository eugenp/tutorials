package com.baeldung.typechecker;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class NonNullExample {

    private static int countArgs(@NonNull String[] args) {
        return args.length;
    }

    public static void main(@Nullable String[] args) {
        System.out.println(countArgs(args));
    }

}
