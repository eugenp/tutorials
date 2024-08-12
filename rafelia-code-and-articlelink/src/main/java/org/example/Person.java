package org.example;
public class Person {
    String name;
    Food food;

    public Person(String name, Food food) {
        this.name = name;
        this.food = food;
    }

    // Shallow Copy Constructor
    public Person(Person other) {
        this.name = other.name;
        this.food = other.food;
    }

    // Deep Copy Constructor
    public Person deepCopy(Person other) {
        return new Person(other.name, new Food(other.food));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
