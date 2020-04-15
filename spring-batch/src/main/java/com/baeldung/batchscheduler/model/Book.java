package com.baeldung.batchscheduler.model;

public class Book {
    private int id;
    private String name;
    
    public Book() {}

    public Book(int id, String name) {
        super();
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
    
    public String toString() {
        return "Book [id=" + id + ", name=" + name + "]";
    }

}
