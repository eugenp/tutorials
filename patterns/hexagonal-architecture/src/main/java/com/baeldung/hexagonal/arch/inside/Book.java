package com.baeldung.hexagonal.arch.inside;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

}
