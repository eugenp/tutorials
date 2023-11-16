package com.baeldung.entity_dto_differences.controller;

import com.baeldung.entity_dto_differences.dto.UserCreationDto;
import com.baeldung.entity_dto_differences.dto.UserResponseDto;
import com.baeldung.entity_dto_differences.entity.User;
import com.baeldung.entity_dto_differences.mapper.UserMapper;
import com.baeldung.entity_dto_differences.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserResponseDto> getUsers() {

        return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserCreationDto userCreationDto) {

        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userCreationDto)));
    }
}
