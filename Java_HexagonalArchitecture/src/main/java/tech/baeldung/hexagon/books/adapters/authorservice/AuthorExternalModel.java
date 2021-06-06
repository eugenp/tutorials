package tech.baeldung.hexagon.books.adapters.authorservice;

import tech.baeldung.hexagon.books.domain.model.Author;
import tech.baeldung.hexagon.books.domain.model.AuthorId;
import tech.baeldung.hexagon.books.domain.model.PersonName;

class AuthorExternalModel {
    private final long id;
    private final String firstName;
    private final String lastName;

    private AuthorExternalModel(final long id, final String firstName, final String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    Author toDomain() {
        return Author.author()
                .withId(AuthorId.of(String.valueOf(id)))
                .withName(PersonName.of(fullName()))
                .build();
    }

    private String fullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String toString() {
        return fullName();
    }

    static AuthorExternalModelBuilder authorExternalModel() {
        return new AuthorExternalModelBuilder();
    }

    static final class AuthorExternalModelBuilder {
        private long id;
        private String firstName;
        private String lastName;

        private AuthorExternalModelBuilder() {
        }

        AuthorExternalModelBuilder withId(long id) {
            this.id = id;
            return this;
        }

        AuthorExternalModelBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        AuthorExternalModelBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        AuthorExternalModel build() {
            return new AuthorExternalModel(id, firstName, lastName);
        }
    }
}
