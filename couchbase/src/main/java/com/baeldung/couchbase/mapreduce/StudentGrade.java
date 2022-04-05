package com.baeldung.couchbase.mapreduce;

public class StudentGrade {

    private String name;
    private String course;
    private Integer grade;
    private Integer hours;
    
    public StudentGrade() { }
    
    public StudentGrade(String name, String course, Integer grade, Integer hours) {
        this.name = name;
        this.course = course;
        this.grade = grade;
        this.hours = hours;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCourse() {
        return course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public Integer getGrade() {
        return grade;
    }
    
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }
}
