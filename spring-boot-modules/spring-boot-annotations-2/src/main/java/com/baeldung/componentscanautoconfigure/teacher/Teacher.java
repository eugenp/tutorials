package com.baeldung.componentscanautoconfigure.teacher;

import org.springframework.stereotype.Component;

@Component("teacher")
public class Teacher {

    @Override
    public String toString() {
        return "Teacher" + this.hashCode();
    }
}
