package com.baeldung.rxjava2;

import lombok.Data;
import lombok.NonNull;

@Data
public class Member {
  @NonNull private int id;
  @NonNull private String firstName;
  @NonNull private String lastName;
  @NonNull private String email;
}
