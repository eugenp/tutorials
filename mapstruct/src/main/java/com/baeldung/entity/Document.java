package com.baeldung.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Document {
    private int id;
    private String title;
    private String text;
    private Date modificationTime;
}
