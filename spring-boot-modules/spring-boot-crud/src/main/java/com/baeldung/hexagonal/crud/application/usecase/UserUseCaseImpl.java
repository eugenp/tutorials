package com.baeldung.hexagonal.crud.application.usecase;

import com.baeldung.hexagonal.crud.application.dto.UserDto;
import com.baeldung.hexagonal.crud.application.mapper.UserMapper;
import com.baeldung.hexagonal.crud.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserUseCaseImpl implements UserUseCase {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto findById(long id) {
        UserDto userDto= this.userMapper.map(this.userService.findById(id));
        return userDto;
    }

    @Override
    public UserDto addUser(String name, String email) {

        this.validateRequiredFields(name, email);
        return this.userMapper.map(this.userService.addUser(name, email));
    }

    @Override
    public UserDto updateUser(long id, String name, String email) {

        this.validateRequiredFields(name, email);
        return this.userMapper.map(this.userService.updateUser(id,name, email));
    }

    @Override
    public void deleteUser(long id) {
        this.userService.deleteUser(id);
    }

    private void validateRequiredFields(String name, String email){
        Assert.notNull(name, "User name cant not be null");
        Assert.notNull(email, "User email cant not be null");
    }
}
