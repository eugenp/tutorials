package com.baeldung.student.service.dbimpl;

import com.baeldung.student.service.StudentService;
import com.baeldung.student.model.Student;
import java.util.logging.*;

public class StudentDbService implements StudentService {

    private static Logger logger = Logger.getLogger("StudentDbService");

    public String create(Student student) {
        logger.log(Level.INFO, "Creating student in DB...");
        return student.getRegistrationId();
    }

    public Student read(String registrationId) {
        logger.log(Level.INFO, "Reading student from DB...");
        return new Student();
    }

    public Student update(Student student) {
        logger.log(Level.INFO, "Updating sutdent in DB...");
        return student;
    }

    public String delete(String registrationId) {
        logger.log(Level.INFO, "Deleteing sutdent in DB...");
        return registrationId;
    }
}