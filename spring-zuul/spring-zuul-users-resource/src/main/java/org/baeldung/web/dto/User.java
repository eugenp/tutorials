package org.baeldung.web.dto;

public class User {
    private long id;
    private String username;
    private int age;

    public User() {
        super();
    }

    public User(final long id, final String name, int age) {
        super();

        this.id = id;
        this.username = name;
        this.age = age;
    }

    //

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}