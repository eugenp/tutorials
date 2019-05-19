package hexagonal.application.port.output;

import hexagonal.domain.User;

public interface FindUser {
    User byName(String name);
}
