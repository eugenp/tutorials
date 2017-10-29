package org.baeldung.spring.service.impl;

import org.baeldung.spring.model.User;
import org.baeldung.spring.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Override
    public void save(User user) {
        System.out.println(String.format("%s Saved Successfully!",user.getName()));
    }
}
