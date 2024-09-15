package com.baeldung.javafeatures.entities;

class Title {

    private String name;

    public Title(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Title deepCopy() {
        return new Title(name);
    }

}