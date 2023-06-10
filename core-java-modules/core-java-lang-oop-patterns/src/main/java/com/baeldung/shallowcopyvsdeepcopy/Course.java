package com.baeldung.shallowcopyvsdeepcopy;

public class Course {
    private int courseId;
    private String courseName;
    private Author author;

    public Course(int courseId, String courseName, Author author) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.author = author;
    }

    public Course(Course that) {
        this.courseId = that.getCourseId();
        this.courseName = that.getCourseName();
        this.author = that.getAuthor();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
