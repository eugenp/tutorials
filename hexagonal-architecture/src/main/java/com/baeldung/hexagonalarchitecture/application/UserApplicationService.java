package com.baeldung.hexagonalarchitecture.application;

import com.baeldung.hexagonalarchitecture.domain.user.InvalidUserStatusToDelete;
import com.baeldung.hexagonalarchitecture.domain.user.User;
import com.baeldung.hexagonalarchitecture.domain.user.UserNotFoundException;
import com.baeldung.hexagonalarchitecture.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * This class has a function of "port" to domain {@link User}
 *
 * @author José Carlos Mazella Junior
 * @version 1.0 10/12/2018
 */
@Service
@AllArgsConstructor
public class UserApplicationService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(final User user) {
        return userRepository.save(user);
    }

    public User getUserById(final Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with id '%s' not found", id)));
    }

    @Transactional
    public void deleteUser(final Long id) {
        final User user = getUserById(id);
        if (user.canBeDeleted()) {
            userRepository.delete(user);
            return;
        }
        throw new InvalidUserStatusToDelete(String.format("Invalid status to delete user '%s'", user.getName()));
    }

}
