package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {

	private TeacherFinder teacherFinder;

	@Autowired
	public Student(TeacherFinder teacherFinder) {
		this.teacherFinder = teacherFinder;
	}

	public String getTeacher() {
        return teacherFinder.getTeacherFinder();
    }
	// business logic that actually uses the injected teacherFinders is omitted...
}

