package com.baeldung.PassingListAsCucumberParameter;

import io.cucumber.java.en.Given;

import java.util.List;

public class DataTableLoginSteps {
    @Given("the user tries to log in with the following accounts:")
    public void loginUser(List<UserCredentials> credentialsList) {
        for (UserCredentials credentials : credentialsList) {
            System.out.println("Username: " + credentials.getUsername() + ", Password: " + credentials.getPassword());
        }
    }

    static class UserCredentials {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
