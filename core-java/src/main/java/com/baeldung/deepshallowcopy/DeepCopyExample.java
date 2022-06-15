package com.baeldung.deepshallowcopy;

public class DeepCopyExample {
    public static void main(String[] args) throws CloneNotSupportedException {
        DeepAddress deepAddress = new DeepAddress("SomeStreet", "City");
        DeepPerson deepPerson = new DeepPerson("Name", "Surname", deepAddress);
        ShallowPerson clonedPerson = (ShallowPerson) deepPerson.clone();
        deepAddress.setStreet("AnotherStreet");
    }
}
