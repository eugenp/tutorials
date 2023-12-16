package com.baeldung.copying.deep;

import java.util.ArrayList;
import java.util.List;

class DeepCopying implements Cloneable {

    private String name;
    private List<String> subjects;

    public DeepCopying() {
    }

    public Object clone() throws CloneNotSupportedException {

        DeepCopying cloned = (DeepCopying) super.clone();

        cloned.subjects = new ArrayList<>(this.subjects);
        return cloned;
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
