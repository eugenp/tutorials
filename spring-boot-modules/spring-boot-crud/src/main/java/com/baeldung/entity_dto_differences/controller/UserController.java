package com.baeldung.entity_dto_differences.controller;

import com.baeldung.entity_dto_differences.dto.UserDto;
import com.baeldung.entity_dto_differences.mapper.UserMapper;
import com.baeldung.entity_dto_differences.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<UserDto> getUsers() {

        return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
}
