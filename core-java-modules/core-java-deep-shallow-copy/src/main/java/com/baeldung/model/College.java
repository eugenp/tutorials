package com.baeldung.model;

import java.io.Serializable;

public class College implements Serializable, Cloneable {

    private static final long serialVersionUID = -1626997389856632147L;
    private String name;
    private String email;

    public College(College college) {
        this.name = college.getName();
        this.email = college.getEmail();
    }

    public College(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public College() {
    }

    //Defined clone method in College class.
    @Override
    protected Object clone() {
        try {
            return (College) super.clone();
        } catch (CloneNotSupportedException e) {
            return new College(this.getName(), this.getEmail());
        }
    }

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
}
