package com.baeldung.entitydtodifferences.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.entitydtodifferences.dto.UserCreationDto;
import com.baeldung.entitydtodifferences.dto.UserResponseDto;
import com.baeldung.entitydtodifferences.mapper.UserMapper;
import com.baeldung.entitydtodifferences.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll()
            .stream()
            .map(UserMapper::toDto)
            .collect(Collectors.toList());
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserCreationDto userCreationDto) {
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userCreationDto)));
    }
}
