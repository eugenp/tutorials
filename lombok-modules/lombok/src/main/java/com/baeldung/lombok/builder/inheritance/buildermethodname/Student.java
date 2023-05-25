package com.baeldung.lombok.builder.inheritance.buildermethodname;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Student extends Child {

    private final String schoolName;

    @Builder(builderMethodName = "studentBuilder")
    public Student(String parentName, int parentAge, String childName, int childAge, String schoolName) {
        super(parentName, parentAge, childName, childAge);
        this.schoolName = schoolName;
    }
}
