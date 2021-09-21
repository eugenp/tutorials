package com.baeldung.hexagonal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// todo use @Data?
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private long id;
  private String name;
  private int age;
}
