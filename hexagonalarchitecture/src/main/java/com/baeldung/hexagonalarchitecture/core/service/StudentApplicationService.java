package com.baeldung.hexagonalarchitecture.core.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonalarchitecture.core.domain.StudentApplication;

public interface StudentApplicationService {
	public List<StudentApplication> deleteAll();

	public List<StudentApplication> deleteAll(Iterable<? extends StudentApplication> applications);

	public Optional<StudentApplication> deleteById(Long id);

	public List<StudentApplication> findAll();

	public Optional<StudentApplication> findById(Long id);

	public StudentApplication saveApplication(StudentApplication application);

	public StudentApplication transferApplication(StudentApplication application);
}