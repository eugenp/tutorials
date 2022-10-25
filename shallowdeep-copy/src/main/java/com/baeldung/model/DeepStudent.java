package com.baeldung.model;

import java.util.ArrayList;
import java.util.List;

public class DeepStudent extends Student {
    @Override
    public Student clone() throws CloneNotSupportedException{

        Student student =  super.clone();
        List<String> subjects = new ArrayList<>( student.getSubjects());
        student.setSubjects(subjects);
        return student;
    }
}
