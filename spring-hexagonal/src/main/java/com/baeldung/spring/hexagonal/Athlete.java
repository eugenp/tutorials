package com.baeldung.spring.hexagonal;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "athlete")
public class Athlete implements IAthlete {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  String username = "";
  double marathonTime = 0.0;

  public Athlete(){
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public double getMarathonTime() {
    return marathonTime;
  }

  public void setMarathonTime(double marathonTime) {
    this.marathonTime = marathonTime;
  }

}
