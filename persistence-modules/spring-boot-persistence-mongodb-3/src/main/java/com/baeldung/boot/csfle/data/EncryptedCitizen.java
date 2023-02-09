package com.baeldung.boot.csfle.data;

import org.bson.BsonBinary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("citizens")
public class EncryptedCitizen {

    private String name;
    private BsonBinary email;
    private BsonBinary birthYear;

    public EncryptedCitizen() {
    }

    public EncryptedCitizen(Citizen citizen) {
        this.name = citizen.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BsonBinary getEmail() {
        return email;
    }

    public void setEmail(BsonBinary email) {
        this.email = email;
    }

    public BsonBinary getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(BsonBinary birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "Citizen [name=" + name + ", email=" + email + ", birthYear=" + birthYear + "]";
    }
}
