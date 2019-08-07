package com.baeldung.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DocumentDTO {
    private int id;
    private String title;
    private String text;
    private List<String> comments;
}
