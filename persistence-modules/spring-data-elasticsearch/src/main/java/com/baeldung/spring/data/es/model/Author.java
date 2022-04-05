package com.baeldung.spring.data.es.model;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

import org.springframework.data.elasticsearch.annotations.Field;

public class Author {

    @Field(type = Text)
    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" + "name='" + name + '\'' + '}';
    }
}
