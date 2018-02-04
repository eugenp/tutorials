package com.baeldung.hibernate.pojo;

import java.io.Serializable;

public class PersonName implements Serializable {

    private static final long serialVersionUID = 7883094644631050150L;

    private String name;

    private String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
