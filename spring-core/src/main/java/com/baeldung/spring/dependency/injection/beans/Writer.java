package com.baeldung.spring.dependency.injection.beans;

public class Writer {

    private String name;

    public Writer() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void printWriterName() {
        System.out.println("Writer Name : "+name);
    }

}
