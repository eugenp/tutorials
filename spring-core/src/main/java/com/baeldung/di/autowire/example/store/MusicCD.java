package com.baeldung.di.autowire.example.store;

import org.springframework.stereotype.Component;

@Component("musicCD")
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
