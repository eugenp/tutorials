package net.baeldung.services;

import net.baeldung.transfer.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

        public boolean fakeAuthenticate(LoginForm lf) {
                return (lf.getUsername().equals(lf.getUsername()) && lf.getPassword() == lf.getPassword());
        }
}