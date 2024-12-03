package com.splunk.log4j.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.splunk.log4j.dto.Student;

@Service
public class StudentService {

    private static final Logger logger = LogManager.getLogger(StudentService.class);

    private final List<Student> students = new ArrayList<>();

    public Student addStudent(Student student) {
        logger.info("addStudent: adding Student");
        logger.info("addStudent: Request: {}", student);
        students.add(student);
        logger.info("addStudent: added Student");
        logger.info("addStudent: Response: {}", student);
        return student;
    }

    public List<Student> getStudents() {
        logger.info("getStudents: getting Students");
        List<Student> studentsList = students;
        logger.info("getStudents: got Students");
        logger.info("getStudents: Response: {}", studentsList);
        return studentsList;
    }

    public Student getStudent(int rollNumber) {
        logger.info("getStudent: getting Student");
        logger.info("getStudent: Request: {}", rollNumber);
        Student student = students.stream()
          .filter(stu -> stu.getRollNumber() == rollNumber)
          .findAny()
          .orElseThrow(() -> new RuntimeException("Student not found"));
        logger.info("getStudent: got Student");
        logger.info("getStudent: Response: {}", student);
        return student;
    }
}
