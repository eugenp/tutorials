package com.baeldung.spring.data.couchbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class StudentRepositoryServiceLiveTest extends StudentServiceLiveTest {

    @Autowired
    @Qualifier("StudentRepositoryService")
    public void setStudentService(StudentService service) {
        this.studentService = service;
    }
}
