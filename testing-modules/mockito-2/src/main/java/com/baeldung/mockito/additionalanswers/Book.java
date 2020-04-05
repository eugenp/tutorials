package com.baeldung.mockito.additionalanswers;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue @Getter
    private Long bookId;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String author;
    @Getter @Setter
    private int numberOfPages;

    public Book(String title, String author, int numberOfPages) {
        this.title = title;
        this.author = author;
        this.numberOfPages = numberOfPages;
    }
}
