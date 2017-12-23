package com.baeldung.di.types.xml;

public class Doctor {

    private String name;
    private String branch;

    public Doctor(String name, String branch) {
        this.name = name;
        this.branch = branch;
    }

    public Doctor() {
    }

    public String getName() {
        return name;
    }

    public String getBranch() {
        return branch;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
