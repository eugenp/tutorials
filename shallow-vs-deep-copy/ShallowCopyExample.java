package com.example.movie;

import java.util.ArrayList;
import java.util.List;

class Movie implements Cloneable {
    private String title;
    private List<String> actors;

    public Movie() {
        this.actors = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", actors=" + actors +
                '}';
    }

    public static void main(String[] args) {
        try {
            // Creating a movie
            Movie originalMovie = new Movie();
            originalMovie.setTitle("Inception");
            List<String> actors = new ArrayList<>();
            actors.add("Leonardo DiCaprio");
            originalMovie.setActors(actors);

            // Shallow copy
            Movie copiedMovie = (Movie) originalMovie.clone();

            // Modifying the copied movie
            copiedMovie.getActors().add("Tom Hardy");

            // Printing both movies
            System.out.println("Original Movie: " + originalMovie);
            System.out.println("Copied Movie: " + copiedMovie);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
