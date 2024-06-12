package com.example.movie;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Movie implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
    private List<String> actors;

    // Constructor
    public Movie() {
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

public class DeepCopyExample {
    public static void main(String[] args) {
        // Create and modify the original movie
        Movie originalMovie = new Movie();
        originalMovie.setTitle("Inception");
        List<String> actors = new ArrayList<>();
        actors.add("Leonardo DiCaprio");
        originalMovie.setActors(actors);

        // Create a deep copy of the movie
        Movie copiedMovie = originalMovie.deepCopy();

        // Modify the copied movie
        copiedMovie.getActors().add("Tom Hardy");

        // Print both movies
        System.out.println("Original Movie: " + originalMovie);
        System.out.println("Copied Movie: " + copiedMovie);
    }
}
