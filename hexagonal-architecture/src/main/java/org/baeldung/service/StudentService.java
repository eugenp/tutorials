package org.baeldung.service;

import javassist.NotFoundException;
import org.baeldung.adapter.secondary.StudentRepositoryAdapter;
import org.baeldung.entity.Student;
import org.baeldung.port.outbound.IRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IRepositoryPort {

    @Autowired
    private StudentRepositoryAdapter studentRepositoryAdapter;

    @Override
    public Student create(Student student) {
        return studentRepositoryAdapter.save(student);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepositoryAdapter.findById(id);
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        if (!studentRepositoryAdapter.findAll().isEmpty()){
            students =  studentRepositoryAdapter.findAll();
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("myFile.txt"), StandardCharsets.UTF_8))) {
                for(Student student : students) {
                    writer.write(student.getAge() +"-" +student.getFirstName() +"-" + student.getSchoolFees() + System.lineSeparator());
                }
                writer.close();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return students;
        }
        return (List<Student>) new NotFoundException("no methos  " + students);
    }
}
