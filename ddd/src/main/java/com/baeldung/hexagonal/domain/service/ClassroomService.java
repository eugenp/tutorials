package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.domain.Classroom;
import com.baeldung.hexagonal.domain.Student;
import com.google.common.collect.Lists;

import java.util.List;

public class ClassroomService {
    public static List<Classroom> transferStudent(Classroom sourceClassroom, Classroom targetClassroom, Student student) {
        sourceClassroom.removeStudent(student);
        targetClassroom.addStudent(student);
        return Lists.newArrayList(sourceClassroom, targetClassroom);
    }
}
