package com.baeldung.shallowcopyvsdeepcopy;

public class Student {
    private int studentId;
    private String studentName;
    private Course course;

    public Student(int studentId, String studentName, Course course) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.course = course;
    }

    public Student(Student that) {
        this.studentId = that.getStudentId();
        this.studentName = that.getStudentName();
        this.course = new Course(that.getCourse()
            .getCourseId(), that.getCourse()
            .getCourseName(), that.getCourse()
            .getAuthor());
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
