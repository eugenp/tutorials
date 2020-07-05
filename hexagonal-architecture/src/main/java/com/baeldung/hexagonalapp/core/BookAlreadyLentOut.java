package com.baeldung.hexagonalapp.core;

class BookAlreadyLentOut extends IllegalStateException {
    public BookAlreadyLentOut() {
        super("This book has already been lent out");
    }
}
