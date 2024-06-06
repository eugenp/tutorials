package com.baeldung.deepshallowcopy;

class Person {

    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Method for deep copy (assuming name is a String object)
    public Person deepCopy() {
        return new Person(new String(this.name), this.age);
    }
}

public class ShallowDeepCopyExample {

    public static void main(String[] args) {
        Person originalPerson = new Person("Sundar", 30);

        // Shallow copy - only reference to the object is copied
        Person copiedPerson = originalPerson;

        // Modify copiedPerson's name
        copiedPerson.name = "Bob";

        // This will also modify the original person's name
        // because both references point to the same object
        System.out.println("Original person name after shallow copy: " + originalPerson.name);  // Output: Bob

        // Deep copy - create a new Person object with copied values
        Person deepCopiedPerson = originalPerson.deepCopy();

        // Modify deepCopiedPerson's name
        deepCopiedPerson.name = "Charlie";

        // This will NOT modify the original person's name
        // because deep copy creates a new object with its own data
        System.out.println("Original person name after deep copy: " + originalPerson.name);  // Output: Bob
    }
}
