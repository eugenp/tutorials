package com.baeldung.core.modifiers.otherpackage;

import com.baeldung.core.modifiers.FirstClass;
//import com.baeldung.core.modifiers.FirstClass.InnerClass;

public class SecondGenericClass {

    // uncomment the following lines to see the errors
    public static void main(String[] args) {
        // accessing protected constructor
        // FirstClass first = new FirstClass("random name");
        // using protected method
        // System.out.println("FirstClass name is " + first.getName());
        // accessing a protected field
        // first.name = "new name";
        // instantiating protected inner class
        // FirstClass.InnerClass innerClass = new FirstClass.InnerClass();
    }

}
