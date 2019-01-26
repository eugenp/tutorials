package com.baeldung.hibernate.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private long studentId;

    public long getStudentId() {
        return studentId;
    }

    public void setStudent_id(long studentId) {
        this.studentId = studentId;
    }
    
}
