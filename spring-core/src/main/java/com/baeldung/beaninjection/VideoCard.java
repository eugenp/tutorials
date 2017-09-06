package com.baeldung.beaninjection;

class VideoCard {

    private String name;

    VideoCard(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}
