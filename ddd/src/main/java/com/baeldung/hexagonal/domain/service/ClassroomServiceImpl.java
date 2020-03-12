package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.application.PersistenceClassroomPort;
import com.baeldung.hexagonal.application.PersistenceStudentPort;
import com.baeldung.hexagonal.domain.Classroom;
import com.baeldung.hexagonal.domain.Student;

public class ClassroomServiceImpl implements ClassroomService {
    private final PersistenceClassroomPort persistenceClassroom;
    private final PersistenceStudentPort persistenceStudent;

    public ClassroomServiceImpl(PersistenceClassroomPort persistenceClassroom, PersistenceStudentPort persistenceStudent) {
        this.persistenceClassroom = persistenceClassroom;
        this.persistenceStudent = persistenceStudent;
    }

    public void transferStudent(String sourceClassroomName, String targetClassroomName, String studentName) {
        Classroom sourceClassroom = persistenceClassroom.find(sourceClassroomName);
        Classroom targetClassroom = persistenceClassroom.find(targetClassroomName);
        Student student = persistenceStudent.find(studentName);

        sourceClassroom.removeStudent(student);
        targetClassroom.addStudent(student);
    }
}
