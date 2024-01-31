package com.baeldung.deepvsshallowcopy;

public class Category implements Cloneable {

    private String name;
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Category(this.getName(), this.getDescription());
        }
    }
}
