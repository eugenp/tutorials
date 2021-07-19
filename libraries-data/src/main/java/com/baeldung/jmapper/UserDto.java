package com.baeldung.jmapper;

import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;

import java.time.LocalDate;
import java.time.Period;

public class UserDto {
    
    @JMap
    private long id;

    @JMap("email")
    private String username;
    
    @JMap("birthDate")
    private int age;

    @JMapConversion(from={"birthDate"}, to={"age"})
    public int conversion(LocalDate birthDate){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    // constructors
    
    public UserDto() {
        super();
    }

    public UserDto(long id, String username, int age) {
        super();
        this.id = id;
        this.username = username;
        this.age = age;
    }

   // getters and setters
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", username=" + username + ", age=" + age + "]";
    }
    
}
