package com.example.hexagonalarch.adapter.driving;

import com.example.hexagonalarch.api.request.UserCreateRq;
import com.example.hexagonalarch.bo.UserBo;
import com.example.hexagonalarch.mapper.UserCreateRqMapper;
import com.example.hexagonalarch.port.driving.CreateUserPort;
import com.example.hexagonalarch.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestAdapter implements CreateUserPort {

    @Autowired
    private UserBo userBo;

    @PostMapping(value = "/user", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<Boolean> createUser(@RequestBody UserCreateRq userCreateRq) {
        User user = UserCreateRqMapper.mapToUser(userCreateRq);
        boolean isUserCreated = userBo.createUser(user);
        return ResponseEntity.status(isUserCreated ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR)
            .body(isUserCreated);
    }

    @Override
    public boolean createUser(@RequestBody User user) {
        return userBo.createUser(user);
    }
}
