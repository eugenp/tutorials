package com.example.movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private List<String> actors;

    // Constructor
    public Movie() {
        this.actors = new ArrayList<>();
    }

    public Movie(String title, List<String> actors) {
        this.title = title;
        this.actors = actors;
    }

    // Getter and Setter methods
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

    // Clone method for shallow copy
    @Override
    public Object clone() throws CloneNotSupportedException {
        Movie cloned = (Movie) super.clone();
        cloned.actors = new ArrayList<>(this.actors); // Ensure the list is also copied
        return cloned;
    }

    // Deep copy method
    public Movie deepCopy() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            return (Movie) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "Movie{title='" + title + "', actors=" + actors + "}";
    }
}
