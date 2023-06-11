package com.baeldung.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article {
    private int id;
    private String name;
    private Person author;
}
