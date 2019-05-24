package org.hexagonal.architechture.core;

public class BookGallery {
    private BookViewer bookViewer;
    private BookRepository bookRepository;

    public BookGallery(BookViewer viewer, BookRepository respository) {
        this.bookViewer = viewer;
        this.bookRepository = respository;
    }

    public void displayAllBooks() {
        for (Book book : bookRepository.getAllBooks()) {
            bookViewer.render(book);
        }
    }
}
