package tech.baeldung.hexagon.books.domain.model;

public class PersonName {
    private final String value;

    private PersonName(final String value) {
        this.value = value;
    }

    public static PersonName of(final String content) {
        return new PersonName(content);
    }

    public String value() {
        return value;
    }
}
