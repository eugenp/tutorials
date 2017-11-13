package com.baeldung.injection;

public class Movie {

    private String title;

    private String director;

    public Movie(String title, String director) {
        super();
        this.title = title;
        this.director = director;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Movie [");
        if (title != null) {
            builder.append("title=");
            builder.append(title);
            builder.append(", ");
        }
        if (director != null) {
            builder.append("director=");
            builder.append(director);
        }
        builder.append("]");
        return builder.toString();
    }

}
