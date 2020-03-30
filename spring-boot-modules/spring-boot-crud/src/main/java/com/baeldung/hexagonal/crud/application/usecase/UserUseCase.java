package com.baeldung.hexagonal.crud.application.usecase;


import com.baeldung.hexagonal.crud.application.dto.UserDto;

public interface UserUseCase {

    UserDto findById(long id);
    UserDto addUser(String name, String email);
    UserDto updateUser(long id, String name, String email);
    void deleteUser(long id);
}
