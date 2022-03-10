package com.baeldung.hexagonalarchitecture.infrastructure;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonalarchitecture.domain.dao.UserRepository;
import com.baeldung.hexagonalarchitecture.domain.model.User;
import com.baeldung.hexagonalarchitecture.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String createUser(String username) {
        return userRepository.save(new User(username));
    }

    @Override
    public void activateUser(User user) {
        user.activateUser();
    }
}
