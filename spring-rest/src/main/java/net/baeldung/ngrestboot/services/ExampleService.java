package net.baeldung.ngrestboot.services;

import net.baeldung.ngrestboot.transfer.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

        public boolean fakeAuthenticate(LoginForm lf) {
                return (lf.getUsername().equals(lf.getUsername()) && lf.getPassword() == lf.getPassword());
        }
}