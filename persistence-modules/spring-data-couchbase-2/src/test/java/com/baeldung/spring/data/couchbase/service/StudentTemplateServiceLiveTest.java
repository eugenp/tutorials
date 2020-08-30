package com.baeldung.spring.data.couchbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class StudentTemplateServiceLiveTest extends StudentServiceLiveTest {

    @Autowired
    @Qualifier("StudentTemplateService")
    public void setStudentService(StudentService service) {
        this.studentService = service;
    }
}
