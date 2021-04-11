package com.baeldung.architecture.hexagonal.domain;

import javax.validation.constraints.NotNull;

public class Post {
    @NotNull
    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
