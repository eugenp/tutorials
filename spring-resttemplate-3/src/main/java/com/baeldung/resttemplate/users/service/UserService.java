package com.baeldung.resttemplate.users.service;

import com.baeldung.resttemplate.users.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> getUsers() ;

}
