package com.baeldung.flash_attributes.model;

import org.apache.logging.log4j.util.Strings;

public class Poem {
    private String title;
    private String author;
    private String body;

    public static boolean isValidPoem(Poem poem) {
        return poem != null && Strings.isNotBlank(poem.getAuthor()) && Strings.isNotBlank(poem.getBody())
          && Strings.isNotBlank(poem.getTitle());
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
