package com.baeldung.boot.csfle.data;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("citizens")
public class Citizen {

    private String name;
    private String email;
    private Integer birthYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Citizen [name=" + name + ", email=" + email + ", birthYear=" + birthYear + "]";
    }
}
