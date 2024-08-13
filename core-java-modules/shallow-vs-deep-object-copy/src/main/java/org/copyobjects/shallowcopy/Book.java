package org.copyobjects.shallowcopy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book implements Cloneable {
    private String name;
    private int pageCount;
    private Author author;

    @Override
    public Book clone() throws CloneNotSupportedException {
        Book book = (Book) super.clone();
        return book;
    }
}
