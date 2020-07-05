package com.baeldung.hexagonalapp.application;

public interface ReturnBookUseCase {

    BookDto handle(ReturnBookCommand command);

    class ReturnBookCommand {
        private final Long bookId;

        public ReturnBookCommand(Long bookId) {
            this.bookId = bookId;
        }

        public Long getBookId() {
            return bookId;
        }
    }
}
