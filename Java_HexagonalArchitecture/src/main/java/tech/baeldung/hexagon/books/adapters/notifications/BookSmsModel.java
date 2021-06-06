package tech.baeldung.hexagon.books.adapters.notifications;

import tech.baeldung.hexagon.books.domain.model.Book;

class BookSmsModel {

    public static final String CONTENT = "Please check your email. We have sent you publication details of the book: >>%s<<";
    private final String recipientId;
    private final String text;

    private BookSmsModel(final String recipientId, final String text) {
        this.recipientId = recipientId;
        this.text = text;
    }

    public static BookSmsModel of(Book book) {
        return new BookSmsModel(
                book.author().name().value(),
                String.format(CONTENT, book.title().value()));

    }

    @Override
    public String toString() {
        return text;
    }
}
