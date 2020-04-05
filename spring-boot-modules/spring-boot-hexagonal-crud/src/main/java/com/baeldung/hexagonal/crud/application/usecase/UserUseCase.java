package com.baeldung.hexagonal.crud.application.usecase;

import com.baeldung.hexagonal.crud.application.dto.UserDto;
import com.baeldung.hexagonal.crud.application.mapper.UserMapper;
import com.baeldung.hexagonal.crud.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserUseCase {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public UserDto findById(long id) {
        return this.userMapper.map(this.userService.findById(id));
    }

    public UserDto addUser(String name, String email) {
        this.validateRequiredFields(name, email);
        return this.userMapper.map(this.userService.addUser(name, email));
    }

    public UserDto updateUser(long id, String name, String email) {
        this.validateRequiredFields(name, email);
        return this.userMapper.map(this.userService.updateUser(id,name, email));
    }

    public void deleteUser(long id) {
        this.userService.deleteUser(id);
    }

    private void validateRequiredFields(String name, String email){
        Assert.notNull(name, "User name cant not be null");
        Assert.notNull(email, "User email cant not be null");
    }
}
