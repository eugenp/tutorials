package com.baeldung;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	@Autowired
	private StudentRepository repository;

	public List<StudentEntity> getAllStudents() {
		return repository.findAll();
	}
}
