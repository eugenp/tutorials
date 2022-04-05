package com.baeldung.student.client;

import com.baeldung.student.service.StudentService;
import com.baeldung.student.service.dbimpl.StudentDbService;
import com.baeldung.student.model.Student;

public class StudentClient {

    public static void main(String[] args) {
        StudentService service = new StudentDbService();
        service.create(new Student());
        service.read("17SS0001");
        service.update(new Student());
        service.delete("17SS0001");
    }
}