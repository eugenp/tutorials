package com.baeldung.objectcopy;

public class Student implements Cloneable {
    private Integer rollNo;
    private String name;
    private Course course;

    public Student(Integer rollNo, String name, Course course) {
        this.rollNo = rollNo;
        this.name = name;
        this.course = course;
    }

    public Student(Student copy) {
        this.rollNo = copy.getRollNo();
        this.name = copy.getName();
        this.course = new Course(copy.getCourse().getCode(), copy.getCourse().getName());
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public Student clone() {
        try {
            Student clone = (Student) super.clone();
            clone.setCourse(new Course(this.getCourse().getCode(), this.getCourse().getName()));
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
