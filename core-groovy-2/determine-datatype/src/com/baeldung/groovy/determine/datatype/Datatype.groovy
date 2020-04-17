package com.baeldung.groovy.determine.datatype

class Person { private int ageAsInt }
class Student extends Person {}
 
def stdObject = new Student()

// using membership operator
assert stdObject in Person

// class variables
assert Person.class.getDeclaredField('ageAsInt').type == int.class

// list
def ageList = ['ageAsString','ageAsDouble', 10]

assert ageList.class == ArrayList
assert ageList.getClass() == ArrayList

// map
def ageMap = [ageAsString: '10 years', ageAsDouble: 10.0]
assert ageMap.class == LinkedHashMap

// instanceof operator
int ageAsInt = 10
assert ageAsInt instanceof Integer

// getClass() method
double ageAsDouble = 10.0
assert ageAsDouble.getClass() == Double

// .class 
String ageAsString = "10 years"
assert ageAsString.class == String