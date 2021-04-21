package com.baeldung.hexagonal;

import java.util.List;

class TopBooksViewerCore {
    private LoadBooksList booksList;
    private PrintBooksList printBooksList;
    private ProcessUserInput processUserInput;

    TopBooksViewerCore(LoadBooksList booksList, PrintBooksList printBooksList, ProcessUserInput processUserInput) {
        this.booksList = booksList;
        this.printBooksList = printBooksList;
        this.processUserInput = processUserInput;
    }

    void showTopBooks() {
        if (!this.processUserInput.processUserInput()) {
            return;
        }

        List<Book> books = this.booksList.loadBooksList();
        if (books.isEmpty()) {
            this.processUserInput.presentError("No books were loaded");
        }

        this.printBooksList.printBooksList(books);
    }
}
