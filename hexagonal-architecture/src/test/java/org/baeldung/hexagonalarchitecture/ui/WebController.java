package org.baeldung.hexagonalarchitecture.ui;

import org.baeldung.hexagonalarchitecture.base.User;
import org.baeldung.hexagonalarchitecture.infrastructure.Repository;

import java.util.List;

public class WebController implements Controller {

  private WebEnricher webEnricher;

  @Override
  public List<User> getUsers(Repository repository) {
    return webEnricher.enrich(repository.getUsers());
  }
}
