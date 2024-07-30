package com.baeldung.boot;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @CreatedDate
    private Date createdDate = new Date();

    public Date getCreatedDate() {
        return createdDate;
    }

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
