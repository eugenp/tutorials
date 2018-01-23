package com.baeldung.difftypesdependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author haseeb
 *
 */
@Component
public class ClassConstructorInjection {

	private Student student;

	public ClassConstructorInjection() {
	}
	
	@Autowired
	public ClassConstructorInjection(Student student) {
		this.student = student;
	}
	
	public String getStudentName() {
		return student.name();
	}
}
