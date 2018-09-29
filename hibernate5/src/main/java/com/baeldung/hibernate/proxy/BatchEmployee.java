package com.baeldung.hibernate.proxy;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@BatchSize(size = 5)
public class BatchEmployee implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Boss boss;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
