package com.baeldung.hexagonal.domain.service;

public interface ClassroomService {
    void transferStudent(String sourceClassroomName, String targetClassroomName, String studentName);
}
