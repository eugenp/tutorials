package org.baeldung.scopes;

public class Person {
    private String name;
    private int age;

    public Person() {
    }

    public Person(final String name, final int age) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + "]";
    }

}
