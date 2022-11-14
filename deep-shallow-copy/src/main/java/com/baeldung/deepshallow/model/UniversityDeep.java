package com.baeldung.deepshallow.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UniversityDeep implements Cloneable {
    private String name;
    private String location;
    private CourseDeep course;

    @Override
    public UniversityDeep clone() {
        try {
            UniversityDeep universityDeep =  (UniversityDeep) super.clone();
            universityDeep.course = course.clone();
            return universityDeep;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
