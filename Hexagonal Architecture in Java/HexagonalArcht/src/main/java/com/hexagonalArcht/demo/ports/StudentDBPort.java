package com.hexagonalArcht.demo.ports;

import com.hexagonalArcht.demo.model.Student;

public interface StudentDBPort {
	
	void register(String name);

	Student getStudent(Integer id);

}
