package com.baeldung.spring.data.couchbase2b.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.baeldung.spring.data.couchbase2b.repos.StudentRepository;
import com.baeldung.spring.data.couchbase.model.Student;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository repo;

    @Autowired
    public void setStudentRepository(StudentRepository repo) {
        this.repo = repo;
    }

    public Student findOne(String id) {
        return repo.findOne(id);
    }

    public List<Student> findAll() {
        List<Student> people = new ArrayList<Student>();
        Iterator<Student> it = repo.findAll().iterator();
        while (it.hasNext()) {
            people.add(it.next());
        }
        return people;
    }

    public List<Student> findByFirstName(String firstName) {
        return repo.findByFirstName(firstName);
    }

    public List<Student> findByLastName(String lastName) {
        return repo.findByLastName(lastName);
    }

    public void create(Student student) {
        student.setCreated(DateTime.now());
        repo.save(student);
    }

    public void update(Student student) {
        student.setUpdated(DateTime.now());
        repo.save(student);
    }

    public void delete(Student student) {
        repo.delete(student);
    }
}
