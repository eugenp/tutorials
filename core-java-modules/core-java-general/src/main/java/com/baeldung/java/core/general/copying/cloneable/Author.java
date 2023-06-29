package com.baeldung.java.core.general.copying.cloneable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Author implements Cloneable {
    private int id;
    private String name;
    private String surname;

    public Author(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.surname = author.getSurname();
    }

    @Override
    protected Author clone() throws CloneNotSupportedException {
        return (Author) super.clone();
    }
}
