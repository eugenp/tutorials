package org.hexagonal.ddd.domain;

import lombok.Data;

@Data
public class Article {
    private String id;
    private String title;
    private String author;
}
