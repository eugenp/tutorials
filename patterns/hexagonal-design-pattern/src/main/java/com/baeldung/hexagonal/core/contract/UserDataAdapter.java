package com.baeldung.hexagonal.core.contract;

import com.baeldung.hexagonal.core.contract.dto.UserDTO;

import java.util.List;

public interface UserDataAdapter {

    void addUser(UserDTO user);

    List<UserDTO> getUsers();
}