package com.baeldung.libraries.opencsv.beans;

import com.opencsv.bean.CsvBindByName;

public class NamedColumnBean extends CsvBean {

    @CsvBindByName(column = "name")
    private String name;

    //Automatically infer column name as Age
    @CsvBindByName
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + ',' + age;
    }


}
