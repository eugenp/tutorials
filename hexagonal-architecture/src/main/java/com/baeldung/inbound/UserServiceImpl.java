package com.baeldung.inbound;

import com.baeldung.model.User;
import com.baeldung.outbound.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private int counter = 0;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User createUser(User user) {
        user.userNo = ++counter;
        return userRepository.createUser(user);
    }
}
