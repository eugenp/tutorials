package com.baeldung.easymock;

public class BaeldungArticle {

    public static BaeldungArticle simpleArticle(String title, String content) {
        return new BaeldungArticle(title, content);
    }

    private String title;
    private String content;

    private BaeldungArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String title() {
        return this.title;
    }

    public String content() {
        return this.content;
    }

}