package com.baeldung.deepshallowcopy;

public class CopyExample {
    public static void main(String[] args) {
        Person john = new Person("John", 25);

        // Shallow copy
        Person shallowCopy = john;

        // Deep copy
        Person deepCopy = null;
        try {
            deepCopy = (Person) john.clone();

            // Modifying the copies
            shallowCopy.setName("Shallow");
            deepCopy.setName("Deep");

            // Output
            System.out.println(john.getName());           // "Shallow"
            System.out.println(shallowCopy.getName());    // "Shallow"
            System.out.println(deepCopy.getName());       // "Deep"
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}