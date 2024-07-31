package com.baeldung.externalizable;

import java.io.*;

public class Community implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                '}';
    }
}
