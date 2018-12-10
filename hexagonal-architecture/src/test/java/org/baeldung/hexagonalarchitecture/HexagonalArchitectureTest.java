package org.baeldung.hexagonalarchitecture;

import org.baeldung.hexagonalarchitecture.base.User;
import org.baeldung.hexagonalarchitecture.infrastructure.MySqlRepository;
import org.baeldung.hexagonalarchitecture.infrastructure.Repository;
import org.baeldung.hexagonalarchitecture.ui.Controller;
import org.baeldung.hexagonalarchitecture.ui.WebController;

import java.util.List;

public class HexagonalArchitectureTest {

  @Test
  public void whenGivenAMySqlRepository_itReturnsMySqlUsers() {
    Controller controller = new WebController();
    Repository repository = new MySqlRepository();
    List<User> users = controller.getUsers(repository);
  }
}
