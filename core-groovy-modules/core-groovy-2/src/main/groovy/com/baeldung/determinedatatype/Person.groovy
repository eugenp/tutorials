package com.baeldung.determinedatatype

class Person {

    int ageAsInt
    Double ageAsDouble
    String ageAsString

    Person() {}

    Person(int ageAsInt) { this.ageAsInt = ageAsInt }

    Person(Double ageAsDouble) { this.ageAsDouble = ageAsDouble }

    Person(String ageAsString) { this.ageAsString = ageAsString }
}

class Student extends Person {
}
