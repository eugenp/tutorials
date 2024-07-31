package com.baeldung.boot.csfle.data;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("citizens")
public class EncryptedCitizen {

    private String name;
    private Binary email;
    private Binary birthYear;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Binary getEmail() {
        return email;
    }

    public void setEmail(Binary email) {
        this.email = email;
    }

    public Binary getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Binary birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Citizen [name=" + name + ", email=" + email + ", birthYear=" + birthYear + "]";
    }
}
