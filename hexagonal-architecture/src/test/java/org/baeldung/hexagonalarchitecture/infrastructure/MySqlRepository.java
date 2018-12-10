package org.baeldung.hexagonalarchitecture.infrastructure;

import org.baeldung.hexagonalarchitecture.base.User;

import java.util.ArrayList;
import java.util.List;

public class MySqlRepository implements Repository {

  private static final List<User> USERS = new ArrayList<>();

  static {
    USERS.add(new User("m@sql.com", "M"));
    USERS.add(new User("y@sql.com", "Y"));
    USERS.add(new User("s@sql.com", "S"));
    USERS.add(new User("q@sql.com", "Q"));
    USERS.add(new User("l@sql.com", "L"));
  }

  @Override
  public List<User> getUsers() {
    return USERS;
  }
}
