package com.baeldung.copying.shallow;

import java.util.List;

class ShallowCopy implements Cloneable {
    private String name;
    private List<String> subjects;

    public ShallowCopy() {
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
}