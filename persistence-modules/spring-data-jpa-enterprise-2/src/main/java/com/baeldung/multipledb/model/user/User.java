package com.baeldung.multipledb.model.user;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    @Column(unique = true, nullable = false)
    private String email;
    private Integer status;
    
    @OneToMany
    List<Possession> possessionList;
  
    public User() {
        super();
    }

    public User(String name, String email, Integer status) {
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public List<Possession> getPossessionList() {
        return possessionList;
    }

    public void setPossessionList(List<Possession> possessionList) {
        this.possessionList = possessionList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [name=").append(name).append(", id=").append(id).append("]");
        return builder.toString();
    }

}