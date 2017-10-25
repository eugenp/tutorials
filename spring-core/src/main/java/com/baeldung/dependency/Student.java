package com.baeldung.dependency;

import java.util.List;
import org.springframework.context.annotation.Component;
import org.springframework.context.annotation.Autowired;

@Component
public class Student {

    private String gender;
    private List courses;

    @Autowired
    public Student(String gender, List courses) {
        this.gender = gender;
        this.courses = courses;
    }

    @Autowired
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Autowired
    public void setCourses(List courses) {
        this.courses = courses;
    }

}
