package com.baeldung.cxf.introduction;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class StudentAdapter extends XmlAdapter<StudentImpl, Student> {
    public StudentImpl marshal(Student student) throws Exception {
        if (student instanceof StudentImpl) {
            return (StudentImpl) student;
        }
        return new StudentImpl(student.getName());
    }

    public Student unmarshal(StudentImpl student) throws Exception {
        return student;
    }
}