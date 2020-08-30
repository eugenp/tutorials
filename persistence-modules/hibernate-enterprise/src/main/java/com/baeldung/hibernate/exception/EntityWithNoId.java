package com.baeldung.hibernate.exception;

import javax.persistence.Entity;

@Entity
public class EntityWithNoId {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
