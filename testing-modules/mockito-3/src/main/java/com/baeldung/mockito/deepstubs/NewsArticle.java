package com.baeldung.mockito.deepstubs;

public class NewsArticle {
    String name;
    String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public NewsArticle(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
