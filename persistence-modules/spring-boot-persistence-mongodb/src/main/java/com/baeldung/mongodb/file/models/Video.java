package com.baeldung.mongodb.file.models;

import java.io.InputStream;

public class Video {
    private String title;
    private InputStream stream;
    
    public Video() {
        super();
    }

    public Video(String title) {
        super();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "Video [title=" + title + "]";
    }

}
