package com.chinwe.hexagonalarchitecture.domain.service;

import com.chinwe.hexagonalarchitecture.domain.model.Student;
import com.chinwe.hexagonalarchitecture.domain.responsestatus.Status;
import com.chinwe.hexagonalarchitecture.port.StudentRepository;
import com.chinwe.hexagonalarchitecture.port.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.getStudents();
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.getStudentById(studentId);
    }

    @Override
    public Status addStudent(Student student) {
       return validateStudentData(student);
    }

    @Override
    public Status removeStudent(Long studentId) {
        Student student=studentRepository.getStudentById(studentId);
        if(student==null){
            return new Status(404,"The Request Failed. The Student Do Not Exist ");
        }
         studentRepository.removeStudent(studentId);
        return new Status(201,"Student Was Successfully Removed");

    }
    private Status validateStudentData(Student student){
        if(student.getStudentId()==null||student.getStudentId()<0){
            return new Status(400,"Missing or Bad Student's Id");
        }
        if(student.getStudentName()==null||student.getStudentName().isEmpty()){
            return new Status(400,"Missing Student's Name ");
        }
        if(student.getStudentRegNumber()==null||student.getStudentRegNumber()<0){
            return new Status(400,"Missing or Bad Student's Registration Number");
        }
        studentRepository.addStudent(student);
        return new Status(200,"Student Saved Successfully");
    }

}
