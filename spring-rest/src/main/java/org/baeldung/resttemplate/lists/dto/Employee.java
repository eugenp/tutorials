package org.baeldung.resttemplate.lists.dto;

public class Employee {

    public long id;
    public String firstName;
    public String lastName;
    public String title;

    public Employee()
    {

    }

    public Employee(long id, String firstName, String lastName, String title)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return "Employee #" + id + "[" + lastName + ", " + firstName +  " - " + title + "]";
    }
}
