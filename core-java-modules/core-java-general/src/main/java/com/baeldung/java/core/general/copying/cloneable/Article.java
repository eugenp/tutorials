package com.baeldung.java.core.general.copying.cloneable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Article implements Cloneable {

    private int size;
    private String title;
    private Author author;

    public Article(Article article) {
        this.size = article.getSize();
        this.title = article.getTitle();
        if (article.getAuthor() != null) {
            this.author = new Author(article.getAuthor());
        }
    }

    @Override
    public Article clone() throws CloneNotSupportedException {

        Article articleClone = (Article) super.clone();

        articleClone.setAuthor(author != null ? author.clone() : null);

        return articleClone;
    }
}
