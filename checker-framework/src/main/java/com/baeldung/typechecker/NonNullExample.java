package com.baeldung.typechecker;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class NonNullExample {

    // This method's parameter is annotated in order
    // to tell the pluggable type system that it can never be null.
    private static int countArgs(@NonNull String[] args) {
        return args.length;
    }

    // This method's parameter is annotated in order
    // to tell the pluggable type system that it may be null.
    public static void main(@Nullable String[] args) {

        // Here lies a potential error,
        // because we pass a potential null reference to a method
        // that does not accept nulls.
        // The Checker Framework will spot this problem at compile time
        // instead of runtime.
        System.out.println(countArgs(args));

    }

}
