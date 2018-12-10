package org.baeldung.hexagonalarchitecture.ui;

import org.baeldung.hexagonalarchitecture.base.User;
import org.baeldung.hexagonalarchitecture.infrastructure.Repository;

import java.util.List;

public class MobileController implements Controller {

  private MobileEnricher mobileEnricher;

  @Override
  public List<User> getUsers(Repository repository) {
    return mobileEnricher.enrich(repository.getUsers());
  }
}
