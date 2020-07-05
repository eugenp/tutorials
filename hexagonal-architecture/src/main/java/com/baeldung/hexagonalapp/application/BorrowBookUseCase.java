package com.baeldung.hexagonalapp.application;

public interface BorrowBookUseCase {
    BookDto handle(BorrowBookCommand command);

    class BorrowBookCommand {
        private final Long bookId;
        private final String borrower;

        public BorrowBookCommand(Long bookId, String borrower) {
            this.bookId = bookId;
            this.borrower = borrower;
        }

        public Long getBookId() {
            return bookId;
        }

        public String getBorrower() {
            return borrower;
        }
    }
}
