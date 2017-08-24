package com.baeldung.injectiontypes;

public class Course {
    
    private Teacher teacher;
    private int priority;
    
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

    public void setPriority(int priority) {
        this.priority = priority;
    }
    
}
