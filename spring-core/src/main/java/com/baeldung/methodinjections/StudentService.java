package com.baeldung.methodinjections;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component("studentService")
public abstract class StudentService {

    @Lookup
    public abstract SchoolNotification getSchoolNotification(String name, int marks);

    public String checkResult(String name, int marks) {
        return getSchoolNotification(name, marks).checkResult();
    }
}
