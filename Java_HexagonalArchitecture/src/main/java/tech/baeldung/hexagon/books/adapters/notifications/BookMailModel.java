package tech.baeldung.hexagon.books.adapters.notifications;

import tech.baeldung.hexagon.books.domain.model.Book;

class BookMailModel {

    private static final String SUBJECT = "You have successfully published: >>%s<<";
    private static final String CONTENT = "Check if everything is correct: >>%s<<";

    private final String recipientId;
    private final String subject;
    private final String content;

    private BookMailModel(final String recipientId,
                             final String subject,
                             final String content) {
        this.recipientId = recipientId;
        this.subject = subject;
        this.content = content;
    }

    static BookMailModel of(final Book book) {
        return new BookMailModel(book.author().name().value(),
                String.format(SUBJECT, book.title().value()),
                String.format(CONTENT, book.content().value()));
    }

    @Override
    public String toString() {
        return subject;
    }
}
