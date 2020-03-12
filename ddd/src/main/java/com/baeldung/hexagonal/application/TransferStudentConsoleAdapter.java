package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.service.ClassroomService;

public class TransferStudentConsoleAdapter implements TransferStudentPort {
    private final ClassroomService classroomService;

    TransferStudentConsoleAdapter(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @Override
    public void transferStudent(String sourceClassroomName, String targetClassroomName, String studentName) {
        classroomService.transferStudent(sourceClassroomName, targetClassroomName, studentName);

        System.out.println(String.format("Transferred student %s from classroom %s to %s.",
                studentName,
                sourceClassroomName,
                targetClassroomName
                ));
    }
}
