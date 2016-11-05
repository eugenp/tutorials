package org.baeldung.spring.data.couchbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class StudentTemplateServiceIntegrationTest extends StudentServiceIntegrationTest {

    @Autowired
    @Qualifier("StudentTemplateService")
    public void setStudentService(StudentService service) {
        this.studentService = service;
    }
}
