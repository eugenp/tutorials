package com.baeldung.architecture.hexagonal.framework;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.architecture.hexagonal.domain.Student;
import com.baeldung.architecture.hexagonal.domain.repository.StudentRepository;
import com.baeldung.architecture.hexagonal.framework.persistence.repository.H2Repository;

@Component(value = "h2Adapter")
public class H2DBAdapterForPersistence implements StudentRepository {

    @Autowired
    private H2Repository h2Repository;

    @Override
    public Student add(Student student) {
        student.setId(Math.abs(new Random().nextLong()));
        return h2Repository.insert(student);
    }

    @Override
    public Student update(Student student) {
        return h2Repository.update(student);
    }

    @Override
    public void deleteById(Long id) {
        h2Repository.deleteById(id);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return h2Repository.findById(id);
    }
}
