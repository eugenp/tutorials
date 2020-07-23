package com.baeldung.hexagonal.infrastructure.adapter.mongodb.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class BookMongoDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private Integer bookId;

   
    private String name;

   
    private String description;
     
   
    private String author;
}