/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.hexagon.repository;

import com.baeldung.hexagon.model.Student;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author NandomPC
 */
@Component
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(Arrays.asList(
                new Student(1, "Solomon", "Stephen"),
                new Student(2, "Dwayne", "Johnson"),
                new Student(3, "John", "Cena"),
                new Student(4, "Samuel", "Johnson"))
        );
    }
}


