package com.baeldung.java.core.general.copying.cloneable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ArticleShallow implements Cloneable {

    private int size;
    private String title;
    private Author author;

    @Override
    public ArticleShallow clone() throws CloneNotSupportedException {
        return (ArticleShallow) super.clone();
    }

}
