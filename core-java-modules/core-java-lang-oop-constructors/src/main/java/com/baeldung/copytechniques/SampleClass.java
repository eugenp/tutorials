package com.baeldung.copyconstructor;

import java.io.Serializable;

public class SampleClass implements Serializable {
    private int id;
    private String name;

    public SampleClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "SampleClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}' ;
    }

}

