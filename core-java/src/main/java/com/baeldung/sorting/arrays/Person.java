package com.baeldung.sorting.arrays;

public class Person implements Comparable<Person> {

    private String firstName;
    private String lastName;
    private int age;

    @Override
    public int compareTo(Person o) {
        if (lastName.compareTo(o.getLastName()) > 0) {
            return 1;
        } else if (lastName.compareTo(o.getLastName()) == 0) {
            if(firstName.compareTo(o.getFirstName()) > 0) {
                return 1;
            } else if (firstName.compareTo(o.getFirstName()) == 0) {
                return age - o.getAge();
            }
        }
        return -1;
    }

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", age=" + age +
            '}';
    }
}
