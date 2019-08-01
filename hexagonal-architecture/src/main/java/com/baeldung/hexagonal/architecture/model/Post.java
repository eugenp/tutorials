package com.baeldung.hexagonal.architecture.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "post")
public class Post {
    @Id
    private String id;

    @Column
    private String message;
}
