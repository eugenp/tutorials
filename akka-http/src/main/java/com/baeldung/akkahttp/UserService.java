package com.baeldung.akkahttp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

  private final static List<User> users = new ArrayList<>();

  static {
    users.add(new User(1l, "Alice"));
    users.add(new User(2l, "Bob"));
    users.add(new User(3l, "Chris"));
    users.add(new User(4l, "Dick"));
    users.add(new User(5l, "Eve"));
    users.add(new User(6l, "Finn"));
  }

  public Optional<User> getUser(Long id) {
    return users.stream()
            .filter(user -> user.getId()
                    .equals(id))
            .findFirst();
  }

  public void createUser(User user) {
    users.add(user);
  }

  public List<User> getUsers(){
    return users;
  }

}
