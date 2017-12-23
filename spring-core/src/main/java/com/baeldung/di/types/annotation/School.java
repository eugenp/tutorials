package com.baeldung.di.types.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "autowiredBeanInjectionSchool")
public class School {

    private StudentService studentService;

    private Security security;

    private TeacherService teacherService;

    @Autowired
    private Cafeteria cafeteria;

    @Autowired
    public School(Security security) {
        this.security = security;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void newTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public Security getSecurity() {
        return security;
    }

    public TeacherService getTeacherService() {
        return teacherService;
    }

    @Override
    public String toString() {
        return "School{" +
                "studentService=" + studentService +
                ", security=" + security +
                ", teacherService=" + teacherService +
                ", cafeteria=" + cafeteria +
                '}';
    }
}
