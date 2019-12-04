package com.baeldung.hexagon.articles.domain.model;

public class Article {
    private final ArticleId id;
    private final Title title;
    private final Content content;
    private final Author author;


    private Article(final ArticleId id, final Title title, final Content content, final Author author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public ArticleId id() {
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

    public static ArticleBuilder article() {
        return new ArticleBuilder();
    }


    public static final class ArticleBuilder {
        private ArticleId id;
        private Title title;
        private Content content;
        private Author author;

        private ArticleBuilder() {
        }

        public ArticleBuilder withId(ArticleId id) {
            this.id = id;
            return this;
        }

        public ArticleBuilder withTitle(Title title) {
            this.title = title;
            return this;
        }

        public ArticleBuilder withContent(Content content) {
            this.content = content;
            return this;
        }

        public ArticleBuilder withAuthor(Author author) {
            this.author = author;
            return this;
        }

        public Article build() {
            return new Article(id, title, content, author);
        }
    }
}
