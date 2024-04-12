package com.baeldung.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDTO {
    private int id;
    private String name;
    private PersonDTO author;
}
