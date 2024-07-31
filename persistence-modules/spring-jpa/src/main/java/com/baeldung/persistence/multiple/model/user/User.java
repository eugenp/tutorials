package com.baeldung.persistence.multiple.model.user;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(schema = "spring_jpa_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private int age;

    @OneToMany
    List<Possession> possessionList;

    public User() {
        super();
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