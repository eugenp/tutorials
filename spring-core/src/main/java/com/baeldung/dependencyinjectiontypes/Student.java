package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {

	// the Student has a dependency on a teacherFinder
	private TeacherFinder teacherFinder;

	// a constructor so that the Spring container can inject a teacherFinder
	@Autowired
	public Student(TeacherFinder teacherFinder) {
		this.teacherFinder = teacherFinder;
	}

	// business logic that actually uses the injected teacherFinders is omitted...
}

