package com.sumanasaha.hexagonalarchitecturejava.inboundport;


import java.util.List;

import com.sumanasaha.hexagonalarchitecturejava.domain.Student;


/**
 * @author ssaha (21.05.20)
 */

public interface StudentService {

    void addStudentDetails( Student student );

    Student getStudentDetails( String studentId );

    List<Student> listAllStudents();
}
