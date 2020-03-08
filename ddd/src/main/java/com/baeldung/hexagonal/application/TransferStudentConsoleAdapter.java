package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Classroom;
import com.baeldung.hexagonal.domain.Student;
import com.baeldung.hexagonal.domain.service.ClassroomService;

import java.util.List;

public class TransferStudentConsoleAdapter implements TransferStudentPort {
    private final PersistenceClassroomPort persistenceClassroomPort;
    private final PersistenceStudentPort persistenceStudentPort;

    TransferStudentConsoleAdapter(PersistenceClassroomPort persistenceClassroomPort, PersistenceStudentPort persistenceStudentPort) {
        this.persistenceClassroomPort = persistenceClassroomPort;
        this.persistenceStudentPort = persistenceStudentPort;
    }

    @Override
    public void transferStudent(String sourceClassroomName, String targetClassroomName, String studentName) {
        Classroom sourceClassroom = persistenceClassroomPort.find(sourceClassroomName);
        Classroom targetClassroom = persistenceClassroomPort.find(targetClassroomName);
        Student student = persistenceStudentPort.find(studentName);
        List<Classroom> classrooms = ClassroomService.transferStudent(sourceClassroom, targetClassroom, student);
        classrooms.forEach(persistenceClassroomPort::save);

        System.out.println(String.format("Transferred student %s from classroom %s to %s.",
                student.getName(),
                sourceClassroom.getName(),
                targetClassroom.getName()
                ));
    }
}
