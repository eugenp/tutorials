package com.baeldung.deepcopyarraylist;

import java.io.Serializable;
import java.util.Objects;

public class Course implements Serializable, Cloneable {

    private Integer courseId;
    private String courseName;

    public Course() {
    }

    public Course(Integer courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public Course clone() {
        try {
            return (Course) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Course that = (Course) o;

        return Objects.equals(courseId,that.courseId)
            && Objects.equals(courseName,that.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId,courseName);
    }
}
