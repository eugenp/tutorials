package com.baeldung.hexagonalarchitecture.inmemory.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonalarchitecture.domain.StudentApplication;
import com.baeldung.hexagonalarchitecture.repository.StudentApplicationRepository;

@Repository
public class InMemoryRepository implements StudentApplicationRepository {

	private Set<StudentApplication> applications = new TreeSet<>();

	@Override
	public List<StudentApplication> deleteAll() {
		List<StudentApplication> apps = new ArrayList<>(applications);
		applications.clear();
		return apps;
	}

	@Override
	public List<StudentApplication> deleteAll(Iterable<? extends StudentApplication> applications) {
		List<StudentApplication> apps = new ArrayList<>();
		applications.forEach(app -> {
			Optional<StudentApplication> optional = deleteById(app.getId());
			if (optional.isPresent()) {
				apps.add(optional.get());
			}
		});
		return apps;
	}

	@Override
	public Optional<StudentApplication> deleteById(Long id) {
		StudentApplication deletedApplication = null;
		for (StudentApplication app : applications) {
			if (app.getId().equals(id)) {
				deletedApplication = app;
				break;
			}
		}

		if (deletedApplication != null) {
			applications.remove(deletedApplication);
		}

		return Optional.ofNullable(deletedApplication);
	}

	@Override
	public List<StudentApplication> findAll() {
		return new ArrayList<>(applications);
	}

	@Override
	public Optional<StudentApplication> findById(Long id) {
		StudentApplication application = null;
		for (StudentApplication app : applications) {
			if (app.getId().equals(id)) {
				application = app;
				break;
			}
		}
		return Optional.ofNullable(application);
	}

	@Override
	public StudentApplication saveApplication(StudentApplication application) {
		applications.add(application);
		return application;
	}

}
