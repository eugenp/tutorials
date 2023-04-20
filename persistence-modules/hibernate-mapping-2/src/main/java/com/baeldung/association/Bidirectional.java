package com.baeldung.association;

import java.util.List;
import javax.persistence.*;

@Entity
public class Department {
 
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
 
}

@Entity
public class Employee {
 
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
 
}

@Entity
public class Student {
 
    @ManyToMany(mappedBy = "students")
    private List<Course> courses;
 
}

@Entity
public class Course {
 
    @ManyToMany
    @JoinTable(name = "course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;
 
}
