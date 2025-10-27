package com.baeldung.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ProfileController implements Initializable {

    private final UserService userService;
    private User currentUser;

    @FXML
    private Label usernameLabel;

    public ProfileController(UserService userService) {
        this.currentUser = userService.getCurrentUser();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText("Welcome, " + this.currentUser.getName());
    }

    // Placeholder classes for demo
    static class UserService {
        private final User user = new User("Baledung");

        UserService() {
            this.user = new User();
        }

        public User getCurrentUser() {
            return this.user;
        }
    }

    static class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}