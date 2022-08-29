package com.baeldung.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @NotNull
  @Size(min=3, max=255)
  private String name;
  
  @Column(unique = true)
  @NotNull @Email
  private String email;
  
  @NotNull
  @Size(min=6, max=255)
  private String password;
  
  @Column(name = "created_at")
  private Date createdAt;
  
  @Column(name = "updated_at")
  private Date updatedAt;

  public User() {
    updatedAt = new Date();
    createdAt = new Date();
  }
  
  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
    createdAt = new Date();
    updatedAt = new Date();
  }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Name: " + this.name
                + "\nEmail: " + this.email;
    }
}
