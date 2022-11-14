package com.baeldung.deepshallow.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UniversityShallow implements Cloneable {
    private String name;
    private String location;
    private CourseShallow course;

    @Override
    public UniversityShallow clone() {
        try {
            return (UniversityShallow) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
