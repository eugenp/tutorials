package com.baeldung.shallowdeepcopy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Book implements Serializable, Cloneable {
    private String bookName;
    private int publishedYear;

    @Override
    protected Object clone() throws CloneNotSupportedException { return super.clone(); }

    //Copy Constructor
    public Book(Book other) {
        this.bookName = other.bookName;
        this.publishedYear = other.publishedYear;
    }
}
