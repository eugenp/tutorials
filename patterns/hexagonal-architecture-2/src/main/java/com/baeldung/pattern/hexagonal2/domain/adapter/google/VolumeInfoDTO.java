package com.baeldung.pattern.hexagonal2.domain.adapter.google;

import java.io.Serializable;

public class VolumeInfoDTO implements Serializable {
    private String title;

    public VolumeInfoDTO(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
