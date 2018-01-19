package com.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student1 {

	private School school;
	 
    // Setter method based DI
    @Autowired
    public void setSchool(School school) {
        this.school = school;
    }
 
    public void schoolInfo() {
        this.school.info();
    }	
}
