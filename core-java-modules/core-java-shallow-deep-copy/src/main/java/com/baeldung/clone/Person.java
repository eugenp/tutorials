package core;

import core.Address;

public class Person {
    private String name;
    private int age;
    private core.Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Shallow copy implementation
    public Person shallowCopy() {
        return new Person(this.name, this.age, this.address);
    }

    // Deep copy implementation
    public Person deepCopy() {
        Address copiedAddress = new Address(this.address.getCity());
        return new Person(this.name, this.age, copiedAddress);
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    // Setter method for address attribute
    public void setAddress(Address address) {
        this.address = address;
    }

    // setters for name and age attributes
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

//
//
//public class Main {
//    public static void main(String[] args) { Address address = new Address("Bengaluru"); Person original = new Person("Rajat", 29, address); System.out.println(original.getName() + ", " + original.getAge() + ", " + original.getAddress().getCity());
//        // Shallow copy
//        Person shallowCopy = original.shallowCopy(); shallowCopy.setName("Gurwinder"); shallowCopy.setAge(33); shallowCopy.getAddress().setCity("Chandigarh"); System.out.println(original.getName() + ", " + original.getAge() + ", " + original.getAddress().getCity());
//        // Output: Rajat, 29, Chandigarh (Modified by shallow copy)
//        // Deep copy
//        Person deepCopy = original.deepCopy(); deepCopy.setName("Narender"); deepCopy.setAge(37); deepCopy.getAddress().setCity("Shimla"); System.out.println(deepCopy.getName() + ", " + deepCopy.getAge() + ", " + deepCopy.getAddress().getCity());
//        // Output: Narender, 37, Shimla (Modified independently by deep copy) }
//    }
//}

