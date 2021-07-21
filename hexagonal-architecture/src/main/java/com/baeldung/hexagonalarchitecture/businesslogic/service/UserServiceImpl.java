package com.baeldung.hexagonalarchitecture.businesslogic.service;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.businesslogic.repository.UserRepository;
import com.baeldung.hexagonalarchitecture.userside.request.UserCreateRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UUID createUser(UserCreateRequest userCreateRequest) {
        User user = userCreateRequest.getUser();
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public User activeUser(UUID uuid) {
        User user = userRepository.findUser(uuid);
        user.active();
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(UUID uuid) {
        userRepository.delete(uuid);
    }
}
