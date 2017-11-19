package com.baeldung.beaninjectiontypes.models;

import java.util.ArrayList;
import java.util.List;

public class Family {

    private String father;
    private String mother;
    private List<String> children;

    public Family(String father, String mother) {
        this.father = father;
        this.mother = mother;
        this.children = new ArrayList<>();
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

}
