package org.baeldung.hexagonalarchitecture.ui;

import org.baeldung.hexagonalarchitecture.base.User;
import org.baeldung.hexagonalarchitecture.infrastructure.Repository;

import java.util.List;

public interface Controller {
  List<User> getUsers(Repository repository);
}