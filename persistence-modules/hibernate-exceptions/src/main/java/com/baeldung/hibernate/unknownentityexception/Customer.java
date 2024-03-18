package com.baeldung.hibernate.unknownentityexception;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
  @Id
  @GeneratedValue
  private Long id;

  private String email;

  public Customer(){

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Customer{" + "id=" + id + ", email='" + email + '\'' + '}';
  }
}
