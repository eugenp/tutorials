package com.baeldung.graphframes;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "<" + id + "," + name + ">";
    }
}
