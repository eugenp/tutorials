package com.baeldung.hexagonalarchitecture.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.hexagonalarchitecture.domain.Major;
import com.baeldung.hexagonalarchitecture.domain.StudentApplication;
import com.baeldung.hexagonalarchitecture.repository.StudentApplicationRepository;
import com.baeldung.hexagonalarchitecture.service.StudentApplicationService;

public class DefaultStudentApplicationService implements StudentApplicationService {

	@Autowired
	private StudentApplicationRepository repository;

	@Override
	public List<StudentApplication> deleteAll() {
		return repository.deleteAll();
	}

	@Override
	public List<StudentApplication> deleteAll(Iterable<? extends StudentApplication> applications) {
		return repository.deleteAll(applications);
	}

	@Override
	public Optional<StudentApplication> deleteById(Long id) {
		return repository.deleteById(id);
	}

	@Override
	public List<StudentApplication> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<StudentApplication> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public StudentApplication saveApplication(StudentApplication application) {
		if (application.getId() == null) {
			application.setId(System.currentTimeMillis());
		}
		return repository.saveApplication(application);
	}

	@Override
	public StudentApplication transferApplication(StudentApplication application) {
		if (application.getMajor() == null || application.getMajor() == Major.LAW) {
			application.setMajor(Major.SCIENCE);
		} else {
			application.setMajor(Major.LAW);
		}
		return repository.saveApplication(application);
	}
}