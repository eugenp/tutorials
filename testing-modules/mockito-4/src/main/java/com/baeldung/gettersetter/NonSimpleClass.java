package com.baeldung.gettersetter;

public class NonSimpleClass {

    private Long id;
    private String name;
    private String superComplicatedField;

    public NonSimpleClass(Long id, String name, String superComplicatedField) {
        this.id = id;
        this.name = name;
        this.superComplicatedField = superComplicatedField;
    }

    public NonSimpleClass() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperComplicatedField() {
        return superComplicatedField;
    }

    public void setSuperComplicatedField(String superComplicatedField) {
        this.superComplicatedField = superComplicatedField;
    }
}
