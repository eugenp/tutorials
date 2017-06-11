package com.baeldung.dependency.constructor;

import com.baeldung.dependency.domain.User;
import com.baeldung.dependency.repository.IUserRepository;
import com.baeldung.dependency.service.IUserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceConstructorImpl implements IUserService {

    private final IUserRepository userRepository;

    public UserServiceConstructorImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(final User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        userRepository.deleteById(user.getId());
    }

    @Override
    public User findById(final Integer id) {
        return userRepository.findById(id);
    }

}
