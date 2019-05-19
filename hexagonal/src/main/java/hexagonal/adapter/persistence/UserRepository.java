package hexagonal.adapter.persistence;

import hexagonal.domain.User;

public interface UserRepository {
    User findUserByName(String name);
}
