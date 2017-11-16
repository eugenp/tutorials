package com.baeldung.beaninjection.setter;

public class Hobby {

    private String hobbyId;
    private String hobbyName;

    public String getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(String hobbyId) {
        this.hobbyId = hobbyId;
    }

    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }
    
    @Override
    public String toString() {
        return "Hobby{" + "hobbyId='" + hobbyId + '\'' + ", hobbyName='" + hobbyName + '\'' + '}';
    }
}
