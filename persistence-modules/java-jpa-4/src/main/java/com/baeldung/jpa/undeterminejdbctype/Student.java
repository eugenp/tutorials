package com.baeldung.jpa.undeterminejdbctype;

import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Map<String, String> studentDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(Map<String, String> studentDetails) {
        this.studentDetails = studentDetails;
    }
}
