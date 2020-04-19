package com.baeldung.resttemplate.lists.dto;

public class Employee {

    public long id;
    public String title;

    public Employee()
    {

    }

    public Employee(long id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return "Employee #" + id + "["  + title + "]";
    }
}
