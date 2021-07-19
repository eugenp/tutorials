package com.baeldung.idc;

import org.springframework.stereotype.Component;

@Component
public class Book {

    private final int id;

    private final String title;

    private final String author;

    private final String genre;
    
    public Book() {
        this(-1, "", "", "");
    }

    public Book(int id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

}
