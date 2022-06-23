package com.baeldung.javalin.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    public int id;
    public String name;
    
    public User(){}

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
