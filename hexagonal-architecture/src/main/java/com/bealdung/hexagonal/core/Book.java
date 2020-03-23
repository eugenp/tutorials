package com.bealdung.hexagonal.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Book{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String author;
    private int pages;

    //getters and setters
}
