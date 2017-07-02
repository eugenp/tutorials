package com.baeldung.cxf.aegis;

import java.util.Map;

interface CourseRepo {
    String getGreeting();
    void setGreeting(String greeting);
    Map<Integer, Course> getCourses();
    void setCourses(Map<Integer, Course> courses);
    void addCourse(Course course);  
}