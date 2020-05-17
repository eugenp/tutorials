package com.baeldung.hexagonal.architecture.infrastructure.jpa.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.architecture.domain.models.Student;
import com.baeldung.hexagonal.architecture.domain.repositories.StudentRepositoryPort;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.entities.StudentEntity;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.repositories.StudentRepository;

@Repository
public class StudentJpaRepositoryAdpater implements StudentRepositoryPort {
    
    private StudentRepository studentRepository;

    public StudentJpaRepositoryAdpater(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        // TODO Auto-generated method stub
        return studentRepository
            .findAll()
            .stream()
            .map(StudentEntity::toStudent)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Student> findById(int id) {
        // TODO Auto-generated method stub
      return studentRepository.findById(id).map(StudentEntity::toStudent);
    }

    @Override
    public Student save(Student std) {
        // TODO Auto-generated method stub
        return studentRepository.save(StudentEntity.fromStudent(std)).toStudent();
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        studentRepository.deleteById(id);
    }
   
}
