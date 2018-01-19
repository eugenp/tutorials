package com.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {

	private School school;
	 
    // Constructor based DI
    @Autowired
    public Student(School school) {
        this.school = school;
    }
 
    public School getSchool() {
        return school;
    }
}
