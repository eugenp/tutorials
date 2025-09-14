package com.example.items;

/** A Book item that doesn't need any external data when used. */
public class Book extends Item<EmptyContext> {
    private final String title;

    public Book(String title) {
        this.title = title;
    }

    @Override
    public void use(EmptyContext ctx) {
        // ctx is always EmptyContext.INSTANCE; no data to read from it.
        System.out.println("You read the book: " + title);
    }
}
