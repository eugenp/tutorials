package com.baeldung.beaninjection.constructor;

public class Hobby {

    public String hobbyId;
    public String hobbyName;

    public Hobby(String hobbyId, String hobbyName) {
        this.hobbyId = hobbyId;
        this.hobbyName = hobbyName;
    }

    @Override
    public String toString() {
        return "Hobby{" + "hobbyId='" + hobbyId + '\'' + ", hobbyName='" + hobbyName + '\'' + '}';
    }
}
