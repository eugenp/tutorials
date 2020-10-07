package com.baeldung.annotations.componentscanautoconfigure.student;

import org.springframework.stereotype.Component;

@Component("student")
public class Student {

    @Override
    public String toString() {
        return "Student" + this.hashCode();
    }
}
