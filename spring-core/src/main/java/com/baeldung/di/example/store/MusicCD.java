package com.baeldung.di.example.store;

public class MusicCD implements Item {

    private String title = "Beethoven, Symphony";
    private String type = "MusicCD";

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getType() {
        return type;
    }

}
