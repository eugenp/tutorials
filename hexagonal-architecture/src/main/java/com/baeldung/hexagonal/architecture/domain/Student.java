package com.baeldung.hexagonal.architecture.domain;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class Student {

  @Setter private long id;
  private String name;
  private int age;
}
