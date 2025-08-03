package com.baeldung.service;

import com.baeldung.entity.User;
import com.baeldung.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void save(User user) {
        repo.save(user);
    }

    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }
}
