package org.baeldung.spring.di.demo;

public class Author {

    private String authorName;
    private String authorEmail;

    public Author(String authorName, String authorEmail) {
        this.authorName = authorName;
        this.authorEmail = authorEmail;
    }

    public void authorDetails() {
        System.out.println(authorName);
        System.out.println(authorEmail);
    }

}
