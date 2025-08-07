package com.baeldung.service;

import com.baeldung.entity.User;
import com.baeldung.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional("userTransactionManager")
    public User save(User user) {
        return repo.save(user);
    }

    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }
}
