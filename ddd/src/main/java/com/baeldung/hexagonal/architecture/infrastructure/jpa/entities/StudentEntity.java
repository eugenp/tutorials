package com.baeldung.hexagonal.architecture.infrastructure.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baeldung.hexagonal.architecture.domain.models.Student;

@Entity
@Table(name = "students")
public class StudentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    
    public StudentEntity() {
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static StudentEntity fromStudent(Student student) {
        StudentEntity estudent = new StudentEntity();
        estudent.setName(student.getName());
        return estudent;
    }

    public Student toStudent() {
        Student student = new Student();
        student.setId(this.getId());
        student.setName(this.getName());
        return student;
        
    }
   
}
