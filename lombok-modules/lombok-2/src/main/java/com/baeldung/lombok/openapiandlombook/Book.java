package com.baeldung.lombok.openapiandlombook;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class Book {

    private Long id;
    private String name;
    private String author;

}
