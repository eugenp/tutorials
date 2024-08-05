package com.baeldung.dddjmolecules.article;

import org.jmolecules.event.annotation.DomainEvent;

import com.baeldung.dddjmolecules.author.Username;

@DomainEvent
public class ArticlePublishedEvent {
    private final Slug slug;
    private final Username author;
    private final String title;

    public ArticlePublishedEvent(Slug slug, Username author, String title) {
        this.slug = slug;
        this.author = author;
        this.title = title;
    }

    public Slug getSlug() {
        return slug;
    }
}
