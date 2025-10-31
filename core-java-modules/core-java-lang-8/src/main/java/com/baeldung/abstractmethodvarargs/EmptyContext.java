package com.example.items;

/**
 * A tiny singleton context object used when no arguments are needed.
 */
public final class EmptyContext {
    public static final EmptyContext INSTANCE = new EmptyContext();
    private EmptyContext() {}
}
