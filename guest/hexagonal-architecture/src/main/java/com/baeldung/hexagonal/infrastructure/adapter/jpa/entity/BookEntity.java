package com.baeldung.hexagonal.infrastructure.adapter.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOOK")
@Data
@Getter
@Setter
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer bookId;

    @Column
    private String name;

    @Column
    private String description;
     
    @Column
    private String author;
}