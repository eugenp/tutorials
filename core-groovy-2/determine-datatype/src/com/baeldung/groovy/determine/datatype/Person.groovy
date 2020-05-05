package com.baeldung.groovy.determine.datatype

class Person {
    
    private int ageAsInt
    private Double ageAsDouble
    private String ageAsString
    
    Person() {}
    Person(int ageAsInt) { this.ageAsInt =  ageAsInt}
    Person(Double ageAsDouble) { this.ageAsDouble =  ageAsDouble}
    Person(String ageAsString) { this.ageAsString =  ageAsString}    
}
class Student extends Person {}
