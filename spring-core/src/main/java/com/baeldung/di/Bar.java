package com.baeldung.di;

public class Bar {

    private String name;
    private int age;
    private Foo foo;

    public Bar() {
    }

    public Bar(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Foo getFoo() {
        return foo;
    }

   
}
