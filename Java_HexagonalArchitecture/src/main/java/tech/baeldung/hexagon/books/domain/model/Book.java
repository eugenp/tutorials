package tech.baeldung.hexagon.books.domain.model;

public class Book {

    private final BookId id;
    private final Title title;
    private final Content content;
    private final Author author;

    private Book(final BookId id, final Title title, final Content content, final Author author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void validateEligibilityForPublication() {
        verifyForPlagiarism();
        validateTitleLength();
        validateContentLength();
        checkPunctuation();
        checkGrammar();
        checkStyle();
        //TODO: these methods are just placeholders with empty implementation
    }

    public BookId id() {
        return id;
    }

    public Title title() {
        return title;
    }

    public Content content() {
        return content;
    }

    public Author author() {
        return author;
    }

    private void checkGrammar() {
    }

    private void checkStyle() {
    }

    private void checkPunctuation() {
    }

    private void verifyForPlagiarism() {
    }

    private void validateContentLength() {
    }

    private void validateTitleLength() {
    }

    public static BookBuilder book() {
        return new BookBuilder();
    }


    public static final class BookBuilder {
        private BookId id;
        private Title title;
        private Content content;
        private Author author;

        private BookBuilder() {
        }

        public BookBuilder withId(BookId id) {
            this.id = id;
            return this;
        }

        public BookBuilder withTitle(Title title) {
            this.title = title;
            return this;
        }

        public BookBuilder withContent(Content content) {
            this.content = content;
            return this;
        }

        public BookBuilder withAuthor(Author author) {
            this.author = author;
            return this;
        }

        public Book build() {
            return new Book(id, title, content, author);
        }
    }
}
