package org.baeldung.hexagonalarchitecture.infrastructure;

import org.baeldung.hexagonalarchitecture.base.User;

import java.util.List;

public interface Repository {
  List<User> getUsers();
}
