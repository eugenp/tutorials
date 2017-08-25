package com.baeldung.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Course {
    
    private Teacher teacher;
    private int priority;
    
    @Autowired
    public Course( Teacher teacher ) {
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getPriority() {
        return priority;
    }

    @Autowired
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
}
