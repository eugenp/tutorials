package baeldung.repository;

import baeldung.model.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String title;
    private String author;
    private boolean available;

    public BookEntity(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.available = book.isAvailable();
    }

    public Book getBook() {
        Book book = new Book(title, author);
        book.setId(id);
        book.setAvailable(available);
        return book;
    }

}
