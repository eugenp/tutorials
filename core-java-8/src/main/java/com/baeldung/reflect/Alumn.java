package com.baeldung.reflect;

public class Alumn extends Person {
    private String uid;
    public Integer campus;

    public Alumn(String fullName, String uid, Integer campus) {
        super(fullName);
        this.uid = uid;
        this.campus = campus;
    }

    public String getUid() {
        return uid;
    }

    public Integer getCampus() {
        return campus;
    }
}
