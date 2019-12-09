package com.baeldung.method.multiplereturnvalues;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class MultipleReturnValuesUsingTuples {

    Tuple2<String, Integer> getStudentData(){
        
        List<Student> students = null;
        students.stream().filter(s -> s.age == 15).findFirst();
        
        String name = "Alex";
        int age = 15;
        
        Tuple2<String, Integer> studentData = new Tuple2<>(name, age);
        
        return studentData;
    }
    
}
