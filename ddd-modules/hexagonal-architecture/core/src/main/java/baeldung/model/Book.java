package baeldung.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Book {

    private UUID id;
    private final String title;
    private final String author;
    private boolean available;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

}
