package tech.baeldung.hexagon.books.domain.model;

public class Content {
    private final String value;

    private Content(final String value) {
        this.value = value;
    }

    public static Content of(final String content) {
        return new Content(content);
    }

    public String value() {
        return value;
    }
}
