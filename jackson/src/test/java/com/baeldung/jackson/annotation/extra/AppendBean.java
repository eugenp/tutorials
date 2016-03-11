package com.baeldung.jackson.annotation.extra;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

@JsonAppend(attrs = {@JsonAppend.Attr(value = "appendedProperty", include = JsonInclude.Include.ALWAYS)})
public class AppendBean {
    private int id;
    private String name;

    public AppendBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}