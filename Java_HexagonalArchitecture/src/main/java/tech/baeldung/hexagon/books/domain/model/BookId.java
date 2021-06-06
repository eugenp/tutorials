package tech.baeldung.hexagon.books.domain.model;

public class BookId {
    private final String value;

    private BookId(final String value) {
        this.value = value;
    }

    public static BookId of(final String bookId) {
        return new BookId(bookId);
    }

    public String value() {
        return value;
    }
}
