package com.baeldung.graphql.data;

import org.apache.commons.lang3.StringUtils;

public class Author {

    private String name;
    private String surname;

    public Author() {

    }

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return StringUtils.join(getName(), " ", getSurname());
    }

}
