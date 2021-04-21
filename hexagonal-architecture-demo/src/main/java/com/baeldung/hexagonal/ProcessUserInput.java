package com.baeldung.hexagonal;

import java.util.List;

// Driver Port
interface ProcessUserInput {
    boolean processUserInput();

    void presentError(String message);
}

// Driven Port
interface LoadBooksList {
    List<Book> loadBooksList();
}

// Driven Port
interface PrintBooksList {
    void printBooksList(List<Book> books);
}