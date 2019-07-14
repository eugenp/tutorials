package com.baeldung.hexagonal.domain.adapters;

import java.util.List;
import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.domain.ports.GetUserService;
import com.baeldung.hexagonal.domain.ports.UserRepository;


public class GetUserServiceAdapter implements GetUserService {

    private UserRepository userRepository;

    public GetUserServiceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

}
