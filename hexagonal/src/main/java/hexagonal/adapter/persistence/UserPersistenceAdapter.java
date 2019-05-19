package hexagonal.adapter.persistence;

import hexagonal.application.port.output.FindUser;
import hexagonal.domain.User;

public class UserPersistenceAdapter implements FindUser {

    private UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User byName(String name) {
        return userRepository.findUserByName(name);
    }
}
