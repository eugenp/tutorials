package com.baeldung.difftypesdependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author haseeb
 *
 */
@Component
public class ClassSetterInjection {

	private Student student;

	@Autowired
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public String getStudentName() {
		return student.name();
	}
}
