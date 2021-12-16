package com.baeldung.constructorspecification.simple;

/**
 * Created by arash on 16.12.21.
 */

class Person{
    String name;

    public Person(){
        this("Arash");   //ExplicitConstructorInvocation
    }

    public Person(String name){
        this.name = name;
    }
}