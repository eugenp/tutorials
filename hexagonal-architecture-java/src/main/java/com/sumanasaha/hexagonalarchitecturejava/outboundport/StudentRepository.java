package com.sumanasaha.hexagonalarchitecturejava.outboundport;


import java.util.List;

import com.sumanasaha.hexagonalarchitecturejava.domain.Student;


/**
 * @author ssaha (21.05.20)
 */

public interface StudentRepository {

    void addStudent( Student student );

    Student getStudent( String studentId );

    List<Student> getAllStudents();
}
