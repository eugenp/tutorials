package services.implementation;

import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import repository.UserRepository;
import services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
