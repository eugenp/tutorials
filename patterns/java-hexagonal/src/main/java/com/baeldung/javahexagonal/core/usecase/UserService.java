package com.baeldung.javahexagonal.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.javahexagonal.core.domain.User;
import com.baeldung.javahexagonal.core.port.EmailSenderOutputPort;
import com.baeldung.javahexagonal.core.port.UserInputPort;
import com.baeldung.javahexagonal.core.port.UserRepoOutputPort;

@Service
public class UserService implements UserInputPort {

    @Autowired
    private UserRepoOutputPort userRepoPort;

    @Autowired
    private EmailSenderOutputPort emailSenderPort;

    @Override
    public User registerUser(User user) throws Exception {

        User createdUser = userRepoPort.getUserByEmail(user.getEmail());

        if (createdUser == null) {
            createdUser = userRepoPort.saveUser(user);
            emailSenderPort.sendRegisterEmail(createdUser);

        } else {
            throw new Exception("Email already taken");
        }

        return createdUser;
    }
}

    @Override
    public User getUserByEmail(String email) {
        User user = userRepoPort.getUserByEmail(email);
        return user;
    }

    @Override
    public User updateUser(User user) throws Exception {
        User updatedUser = userRepoPort.getUserByEmail(user.getEmail());

        if (updatedUser != null) {
            updatedUser = userRepoPort.updateUser(user);
        } else {
            throw new Exception("User does not exists");
        }

        return updatedUser;
    }

}
