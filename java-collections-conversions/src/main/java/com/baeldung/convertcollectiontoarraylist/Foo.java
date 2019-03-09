package com.baeldung.convertcollectiontoarraylist;

/**
 * This POJO is the element type of our collection. It has a deepCopy() method.
 *
 * @author chris
 */
public class Foo {

    private int id;
    private String name;
    private Foo parent;

    public Foo() {
    }

    public Foo(int id, String name, Foo parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Foo getParent() {
        return parent;
    }

    public void setParent(Foo parent) {
        this.parent = parent;
    }

    public Foo deepCopy() {
        return new Foo(
          this.id, this.name, this.parent != null ? this.parent.deepCopy() : null);
    }

}
