package com.baeldung.hexagonal.services;

import com.baeldung.hexagonal.entities.User;
import com.baeldung.hexagonal.ports.UserUpdatePort;
import com.baeldung.hexagonal.repositories.UserRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class UserService {
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {this.userRepository = userRepository;}

    public void updateUser(Long id, User user, BindingResult result, UserUpdatePort userUpdatePort, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            userUpdatePort.showErrors();
        }

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        userUpdatePort.returnIndexPage();
    }
}
