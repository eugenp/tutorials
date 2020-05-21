package com.sumanasaha.hexagonalarchitecturejava.adapter;


import java.util.List;

import org.springframework.stereotype.Service;

import com.sumanasaha.hexagonalarchitecturejava.domain.Student;
import com.sumanasaha.hexagonalarchitecturejava.inboundport.StudentService;
import com.sumanasaha.hexagonalarchitecturejava.outboundport.StudentRepository;


/**
 * @author ssaha (21.05.20)
 *
 * * This class states communication between Domain and Datasource
 *
 */

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl( final StudentRepository studentRepository ) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void addStudentDetails( final Student student ) {

        studentRepository.addStudent( student );

    }

    @Override
    public Student getStudentDetails( final String studentId ) {

        return studentRepository.getStudent( studentId );

    }

    @Override
    public List<Student> listAllStudents() {

        return studentRepository.getAllStudents();

    }
}
