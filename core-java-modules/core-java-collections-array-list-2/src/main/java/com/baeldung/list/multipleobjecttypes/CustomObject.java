package com.baeldung.list.multipleobjecttypes;

public class CustomObject {
    String classData;
    Integer intData;

    CustomObject(String classData) {
        this.classData = classData;
    }

    CustomObject(Integer intData) {
        this.intData = intData;
    }

    public String getClassData() {
        return this.classData;
    }

    public Integer getIntData() {
        return this.intData;
    }
}
