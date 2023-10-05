package com.baeuldung.java.copy;

/**
 * Represents a student with a name and a course.
 */
public class Student implements Cloneable {

    String name;
    Course course; // Nested object

    /**
     * Constructs a Student object with a given name and course name.
     * @param name The name of the student.
     * @param courseName The name of the course the student is enrolled in.
     */
    public Student(String name, String courseName) {
        this.name = name;
        this.course = new Course(courseName);
    }

    /**
     * Creates a shallow copy of the student object.
     * @return A shallow copy of the student object.
     * @throws CloneNotSupportedException if the object's class does not implement the Cloneable interface.
     */
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Creates a deep copy of the student object, including the course object.
     * @return A deep copy of the student object.
     * @throws CloneNotSupportedException if the object's class does not implement the Cloneable interface.
     */
    protected Object deepClone() throws CloneNotSupportedException {
        Student student = (Student) super.clone();
        student.course = new Course(course.courseName);
        return student;
    }
}

/**
 * Represents a course with a specific course name.
 */
class Course {

    String courseName;

    /**
     * Constructs a Course object with a given course name.
     * @param courseName The name of the course.
     */
    public Course(String courseName) {
        this.courseName = courseName;
    }
}
