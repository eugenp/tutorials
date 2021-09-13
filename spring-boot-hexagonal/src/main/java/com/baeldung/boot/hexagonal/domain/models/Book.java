package com.baeldung.boot.hexagonal.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String[] authors;
    private String description;
    private double price;
    private String publisher;
    private String isbn;
}
