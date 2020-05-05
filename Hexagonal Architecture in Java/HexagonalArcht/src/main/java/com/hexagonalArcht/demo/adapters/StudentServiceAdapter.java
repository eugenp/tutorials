package com.hexagonalArcht.demo.adapters;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hexagonalArcht.demo.model.Student;
import com.hexagonalArcht.demo.ports.StudentDBPort;

@Service
public class StudentServiceAdapter implements StudentDBPort {
@PersistenceContext
private EntityManager entityManager;

@Transactional
@Override
public void register(String name) {
Student student= new Student();
student.setName(name);

entityManager.persist(student);
}

@Override
public Student getStudent(Integer id) {
return entityManager.find(Student.class, id);
}
}