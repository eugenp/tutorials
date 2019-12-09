package com.baeldung.method.multiplereturnvalues;

class MultipleReturnValuesUsingWrappers {

    Student getStudent() {
        
        String name = "Alex";
        int age = 15;
        
        return new Student(name, age);
    }
    
}
