package com.baeldung.booker.domain;

import javax.persistence.*;

@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String category;
  @Column(name = "number_of_copies")
  private int numberOfCopies;

  public Book() {
  }

  public boolean rentABook() {
    if (numberOfCopies <= 0) {
      return false;
    }
    numberOfCopies--;
    return true;
  }

  //Book return
  public boolean returnABook() {
    numberOfCopies++;
    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getNumberOfCopies() {
    return numberOfCopies;
  }

  public void setNumberOfCopies(int numberOfCopies) {
    this.numberOfCopies = numberOfCopies;
  }
}
