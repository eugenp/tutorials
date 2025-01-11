package com.baeldung.passinglistascucumberparameter;

import io.cucumber.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DataTableLoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(DataTableLoginSteps.class);

    @Given("the user tries to log in with the following accounts:")
    public void loginUser(List<UserCredentials> credentialsList) {
        for (UserCredentials credentials : credentialsList) {
            logger.info("Username: {}, Password: {}", credentials.getUsername(), credentials.getPassword());
        }
    }

    public static class UserCredentials {
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
