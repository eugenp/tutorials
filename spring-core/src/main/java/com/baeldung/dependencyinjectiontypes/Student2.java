package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student2 {

	private TeacherFinder teacherFinder;

	@Autowired
    public void setTeacherFinder(TeacherFinder teacherFinder) {
        this.teacherFinder = teacherFinder;
    }
	
	public String getTeacher() {
        return teacherFinder.getTeacherFinder();
    }

}