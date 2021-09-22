package com.baeldung.architecture.clean.hexagonal.entitybased.user.infrastructure.controller;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model.UserCreateUseCase;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.model.mappers.UserMapper;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.exception.DocumentIDInvalidException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserCreateUseCase userCreateUseCase;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userRQ) throws DocumentIDInvalidException {
        User userRS = userCreateUseCase.createUser(userMapper.toUserEntity(userRQ));
        return new ResponseEntity<>(userMapper.toUserDTO(userRS), HttpStatus.OK);
    }
}
