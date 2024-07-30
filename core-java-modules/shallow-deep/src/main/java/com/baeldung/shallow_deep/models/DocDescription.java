package com.baeldung.shallow_deep;

import java.io.Serializable;

public class DocDescription implements Serializable {
    private int id;
    private String type;

    public DocDescription(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "(" + id + ", \"" + type + "\")";
    }
}
