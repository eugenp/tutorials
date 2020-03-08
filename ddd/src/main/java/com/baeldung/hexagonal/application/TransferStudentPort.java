package com.baeldung.hexagonal.application;

public interface TransferStudentPort {
    void transferStudent(String sourceClassroomName, String targetClassroomName, String studentName);
}
