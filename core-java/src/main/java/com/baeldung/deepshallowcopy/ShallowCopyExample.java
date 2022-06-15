package com.baeldung.deepshallowcopy;

public class ShallowCopyExample {
    public static void main(String[] args) throws CloneNotSupportedException {
        ShallowAddress shallowAddress = new ShallowAddress("SomeStreet", "City");
        ShallowPerson shallowPerson = new ShallowPerson("Name", "Surname", shallowAddress);
        ShallowPerson clonedPerson = (ShallowPerson) shallowPerson.clone();
        shallowAddress.setStreet("AnotherStreet");
    }
}
