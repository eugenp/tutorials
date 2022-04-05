package com.baeldung.jackson.optionalwithjackson;

import java.util.Optional;

public class Book {

    private String title;
    private Optional<String> subTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<String> getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(Optional<String> subTitle) {
        this.subTitle = subTitle;
    }
}
