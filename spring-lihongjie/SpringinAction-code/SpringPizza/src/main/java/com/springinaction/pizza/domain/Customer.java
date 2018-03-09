package com.springinaction.pizza.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customer implements Serializable {
  private Integer id;
  private String name;
  private String address;
  private String city;
  private String state;
  private String zipCode;
  private String phoneNumber;
  
  public Customer() {}
  
  public Customer(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
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

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
}
