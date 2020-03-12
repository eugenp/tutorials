package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Classroom;
import com.baeldung.hexagonal.domain.Student;
import com.baeldung.hexagonal.domain.service.ClassroomService;
import com.baeldung.hexagonal.domain.service.ClassroomServiceImpl;

public class ConsoleApplication {

    public static void main(String... args) {
        PersistenceStudentPort persistenceStudent = new PersistenceStudentInMemoryAdapter();
        PersistenceClassroomPort persistenceClassroom = new PersistenceClassroomInMemoryAdapter();
        Student student = new Student(args[2]);
        persistenceStudent.save(student);
        Classroom classroom1 = new Classroom(args[0]);
        classroom1.addStudent(student);
        Classroom classroom2 = new Classroom(args[1]);
        persistenceClassroom.save(classroom1);
        persistenceClassroom.save(classroom2);
        ClassroomService classroomService = new ClassroomServiceImpl(persistenceClassroom, persistenceStudent);

        TransferStudentPort transferStudent = new TransferStudentConsoleAdapter(classroomService);

        transferStudent.transferStudent(args[0], args[1], args[2]);
    }
}
