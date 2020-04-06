package com.baeldung.mockito.additionalanswers;

public class BookRepository {
    public Book getByBookId(Long bookId) {
        return new Book(bookId, "To Kill a Mocking Bird", "Harper Lee", 256);
    }

    public Book save(Book book) {
        return new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getNumberOfPages());
    }

    public Book checkIfEquals(Book bookOne, Book bookTwo, Book bookThree) {
        if (bookOne.equals(bookTwo) && bookTwo.equals(bookThree) && bookThree.equals(bookOne)) {
            return bookOne;
        } else return bookTwo;
    }
}
