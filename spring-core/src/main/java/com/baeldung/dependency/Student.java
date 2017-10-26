package com.baeldung.dependency;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {

    private String gender;
    private List courses;

    @Autowired
    public Student(String gender, List courses) {
        this.gender = gender;
        this.courses = courses;
    }

    //@Autowired
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getGender() {
        return gender;
    }

    //@Autowired
    public void setCourses(List courses) {
        this.courses = courses;
    }
    
    public List getCourses() {
        return courses;
    }

}
