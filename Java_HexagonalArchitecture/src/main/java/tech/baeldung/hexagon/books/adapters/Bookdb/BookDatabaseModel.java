package tech.baeldung.hexagon.books.adapters.Bookdb;

import tech.baeldung.hexagon.books.domain.model.Book;
import tech.baeldung.hexagon.books.domain.model.BookId;
import tech.baeldung.hexagon.books.domain.model.Author;
import tech.baeldung.hexagon.books.domain.model.AuthorId;
import tech.baeldung.hexagon.books.domain.model.Content;
import tech.baeldung.hexagon.books.domain.model.PersonName;
import tech.baeldung.hexagon.books.domain.model.Title;

import java.time.ZonedDateTime;
import java.util.UUID;

class BookDatabaseModel {

    private final UUID id;
    private final String title;
    private final String content;
    private final long version;
    private final ZonedDateTime createdAt;
    private final String authorId;
    private final String authorName;

    private BookDatabaseModel(final UUID id,
                                 final String title,
                                 final String content,
                                 final String authorId,
                                 final long version,
                                 final ZonedDateTime createdAt,
                                 final String authorName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.version = version;
        this.createdAt = createdAt;
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return title;
    }

    Book toDomain() {
        return Book.book()
                .withId(BookId.of(id.toString()))
                .withAuthor(Author
                        .author()
                        .withId(AuthorId.of(authorId))
                        .withName(PersonName.of(authorName))
                        .build())
                .withTitle(Title.of(title))
                .withContent(Content.of(content))
                .build();
    }

    static BookDatabaseModel of(final Author author, final Title title, final Content content) {
        return BookDatabaseModel()
                .withId(UUID.randomUUID())
                .withVersion(0)
                .withCreatedAt(ZonedDateTime.now())
                .withAuthorId(author.id().value())
                .withAuthorName(author.name().value())
                .withTitle(title.value())
                .withContent(content.value())
                .build();
    }

    static BookDatabaseModelBuilder BookDatabaseModel() {
        return new BookDatabaseModelBuilder();
    }

    static final class BookDatabaseModelBuilder {
        private UUID id;
        private String title;
        private String content;
        private long version;
        private ZonedDateTime createdAt;
        private String authorId;
        private String authorName;

        private BookDatabaseModelBuilder() {
        }

        BookDatabaseModelBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        BookDatabaseModelBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        BookDatabaseModelBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        BookDatabaseModelBuilder withVersion(long version) {
            this.version = version;
            return this;
        }

        BookDatabaseModelBuilder withCreatedAt(ZonedDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        BookDatabaseModelBuilder withAuthorId(String authorId) {
            this.authorId = authorId;
            return this;
        }

        BookDatabaseModelBuilder withAuthorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        BookDatabaseModel build() {
            return new BookDatabaseModel(id, title, content, authorId, version, createdAt, authorName);
        }
    }
}
