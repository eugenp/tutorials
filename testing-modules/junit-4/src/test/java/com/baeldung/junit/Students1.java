package com.baeldung.junit;
public class Students1 implements Cloneable {
 String name;
 Courses1 course;

 public Students1(String name, Courses1 course) {
    this.name = name;
    this.course = course;
 }
 @Override
 protected Object clone() throws CloneNotSupportedException {
     Students1 clonedStudent = (Students1) super.clone();
     clonedStudent.course = new Courses1(this.course);
     return clonedStudent;
 }
}
