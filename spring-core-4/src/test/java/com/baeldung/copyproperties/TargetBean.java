package com.baeldung.copyproperties;

public class TargetBean {
    public String name;
    public int age;
    public String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public TargetBean() {
    }

    public TargetBean(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "TargetBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
