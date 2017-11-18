package com.baeldung.cxf.introduction;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Student")
public class StudentImpl implements Student {
    private String name;

    StudentImpl() {
    }

    public StudentImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}