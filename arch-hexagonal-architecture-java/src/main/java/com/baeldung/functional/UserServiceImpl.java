package com.baeldung.functional;

import com.baeldung.persistence.User;
import com.baeldung.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(String username, String firstName, String lastName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            // create the user
            user = new User();
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userRepository.save(user);
        }
        return user;
    }

}
