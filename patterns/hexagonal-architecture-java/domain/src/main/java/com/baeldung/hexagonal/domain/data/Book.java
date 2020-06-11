package com.baeldung.hexagonal.domain.data;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Book {

    private UUID id;
    private String title;
    private String author;
    private String description;

}
