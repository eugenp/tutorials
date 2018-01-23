package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student2 {

    // the Student has a dependency on the TeacherFinder
	private TeacherFinder teacherFinder;

    // a setter method so that the Spring container can inject a TeacherFinder
	@Autowired
    public void setTeacherFinder(TeacherFinder teacherFinder) {
        this.teacherFinder = teacherFinder;
    }

    // business logic that actually uses the TeacherFinder MovieFinder is omitted...
}