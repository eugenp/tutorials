package com.baeldung.example.service;

import org.springframework.stereotype.Service;

import com.baeldung.example.model.User;

@Service
public interface UserService {

	User getUserByUserId(int userId);

	User createNewUser(User user);

}
