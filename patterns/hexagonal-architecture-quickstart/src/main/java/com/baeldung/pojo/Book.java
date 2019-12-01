package com.baeldung.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Book {

    @Id
    private String bookNo;

    private String title;

    private String author;

    private String publisher;

    private String lang;

    private String isbn10;

    private String shortDesc;

    private String status;

}
