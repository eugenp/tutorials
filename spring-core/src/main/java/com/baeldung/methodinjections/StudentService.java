package com.baeldung.methodinjections;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component("studentService")
public abstract class StudentService {

    @Lookup
    public abstract SchoolNotification getSchoolNotification(String message);

}
