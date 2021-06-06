package tech.baeldung.hexagon.books.domain.model;

public class Author {
    private final AuthorId id;
    private final PersonName name;


    private Author(final AuthorId id, final PersonName name) {
        this.id = id;
        this.name = name;
    }

    public static AuthorBuilder author() {
        return new AuthorBuilder();
    }

    public PersonName name() {
        return name;
    }

    public AuthorId id() {
        return id;
    }

    public static final class AuthorBuilder {
        private AuthorId id;
        private PersonName name;

        private AuthorBuilder() {
        }

        public AuthorBuilder withId(AuthorId id) {
            this.id = id;
            return this;
        }

        public AuthorBuilder withName(PersonName name) {
            this.name = name;
            return this;
        }

        public Author build() {
            return new Author(id, name);
        }
    }
}
