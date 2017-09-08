package com.baeldung.beaninjection.domain;

public class Data {

    private String title;
    private String duration;

    public Data(String title, String duration) {
        this.title = title;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Data{" +
                "title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
