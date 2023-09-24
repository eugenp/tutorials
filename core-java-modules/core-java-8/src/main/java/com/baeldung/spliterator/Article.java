package com.baeldung.spliterator;

import java.util.List;

public class Article {
    private List<Author> listOfAuthors;
    private String name;

    public Article(String name) {
        this.name = name;
    }

    public Article(List<Author> listOfAuthors) {
        this.listOfAuthors = listOfAuthors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getListOfAuthors() {
        return listOfAuthors;
    }
}