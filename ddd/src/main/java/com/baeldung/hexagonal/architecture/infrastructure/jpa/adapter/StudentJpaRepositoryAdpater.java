package com.baeldung.hexagonal.architecture.infrastructure.jpa.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.architecture.domain.model.Student;
import com.baeldung.hexagonal.architecture.domain.repository.StudentRepositoryPort;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.entity.StudentEntity;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.repository.StudentRepository;

@Repository
public class StudentJpaRepositoryAdpater implements StudentRepositoryPort {

    private StudentRepository studentRepository;

    public StudentJpaRepositoryAdpater(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll()
            .stream()
            .map(StudentEntity::toStudent)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Student> findById(int id) {
        return studentRepository.findById(id)
            .map(StudentEntity::toStudent);
    }

    @Override
    public Student save(Student std) {
        return studentRepository.save(StudentEntity.fromStudent(std))
            .toStudent();
    }

    @Override
    public void delete(int id) {
        studentRepository.deleteById(id);
    }

}
