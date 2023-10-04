package com.baeuldung.java.copy;

public class Student implements Cloneable {

  String name;
  Course course; // Nested object

  public Student(String name, String courseName) {
    this.name = name;
    this.course = new Course(courseName);
  }

  // Creating a shallow copy
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  // Creating a deep copy
  protected Object deepClone() throws CloneNotSupportedException {
    Student student = (Student) super.clone();
    student.course = new Course(course.courseName);
    return student;
  }
}

class Course {

  String courseName;

  public Course(String courseName) {
    this.courseName = courseName;
  }
}
