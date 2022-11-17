package com.baeldung.largeresultset.service;

import org.springframework.stereotype.Service;

import com.baeldung.largeresultset.Student;

@Service
public class EmailService {

    public void sendEmailToStudent(Student student) {
        System.out.println("sending email to: " + student);
    }

}
