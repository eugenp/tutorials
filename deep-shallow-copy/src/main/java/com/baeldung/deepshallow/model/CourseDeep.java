package com.baeldung.deepshallow.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDeep implements Cloneable {
    private String code;
    private String name;

    @Override
    public CourseDeep clone() {
        try {
            return (CourseDeep) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
