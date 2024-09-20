package com.baeldung.changevalue.entity;

import com.baeldung.changevalue.jpa.PersonEventListener;
import jakarta.persistence.*;

@Entity
@Table(name = "student")
@EntityListeners(PersonEventListener.class)
public class StudentWithEventListener implements Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}