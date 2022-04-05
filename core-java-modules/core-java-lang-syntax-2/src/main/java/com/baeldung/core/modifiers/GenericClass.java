package com.baeldung.core.modifiers;

public class GenericClass {

    public static void main(String[] args) {
        // accessing protected constructor
        FirstClass first = new FirstClass("random name");
        // using protected method
        System.out.println("FirstClass name is " + first.getName());
        // accessing a protected field
        first.name = "new name";
        // instantiating protected inner class
        FirstClass.InnerClass innerClass = new FirstClass.InnerClass();
    }
}
