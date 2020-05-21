package com.sumanasaha.hexagonalarchitecturejava.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sumanasaha.hexagonalarchitecturejava.domain.Student;
import com.sumanasaha.hexagonalarchitecturejava.outboundport.StudentRepository;


/**
 * @author ssaha (21.05.20)
 *
 *  * This class is the adapter for the Outbound port StudentRepository
 *
 */

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private Map<String, Student> studentStore
            = new HashMap<>();

    @Override
    public void addStudent( final Student student ) {

        studentStore.put( student.getId(), student );

    }

    @Override
    public Student getStudent( final String studentId ) {

        return studentStore.get( studentId );

    }

    @Override
    public List<Student> getAllStudents() {

        return new ArrayList<>( studentStore.values() );

    }
}
