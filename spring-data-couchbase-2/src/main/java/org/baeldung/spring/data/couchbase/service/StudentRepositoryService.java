package org.baeldung.spring.data.couchbase.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.baeldung.spring.data.couchbase.model.Student;
import org.baeldung.spring.data.couchbase.repos.StudentRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("StudentRepositoryService")
public class StudentRepositoryService implements StudentService {

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
