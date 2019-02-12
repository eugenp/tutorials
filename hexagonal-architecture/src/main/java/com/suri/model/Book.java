package com.suri.model;
  
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {

    private String title;
    private String isdn;
    private String author;
    private String publisher;
}
