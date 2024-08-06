package com.baeldung.dddjmolecules.author;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.annotation.ValueObject;

import com.baeldung.dddjmolecules.article.Slug;

@AggregateRoot
public class Author {
    @Identity
    private Username username;
    private Email email;
    private Slug latestArticle;

    @ValueObject
    record Email(String address) {
    }

    // constructor, getters, setters
}
