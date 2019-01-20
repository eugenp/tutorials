package com.baeldung.tutorial.hexagonal;

import com.baeldung.tutorial.hexagonal.adapter.InMemoryUserRepositoryImpl;
import com.baeldung.tutorial.hexagonal.adapter.UIUserRegistrationImpl;
import com.baeldung.tutorial.hexagonal.core.RegisteredUserDetailsImpl;
import com.baeldung.tutorial.hexagonal.core.User;
import com.baeldung.tutorial.hexagonal.core.UserMaintenance;
import com.baeldung.tutorial.hexagonal.port.in.UserRegistration;
import com.baeldung.tutorial.hexagonal.port.out.RegisteredUserDetails;
import com.baeldung.tutorial.hexagonal.port.out.UserRepository;

/**
 * In this class all initializations are done through new, but in ideal case, they should be injected.
 */
public class ExampleMain {

    public static void main(String... args) {
        //This can be mapped to either DB or NOSQL also
        UserRepository repository = new InMemoryUserRepositoryImpl();
        UserMaintenance maintenance = new UserMaintenance();
        //this should be autowired which gets the data from core logic
        RegisteredUserDetails registeredUserDetails = new RegisteredUserDetailsImpl();
        ((RegisteredUserDetailsImpl) registeredUserDetails).setUserMaintenance(maintenance);
        maintenance.setUserRepository(repository);
        //This should be autowired which gets the data from UI or from REST API or from different application.
        UserRegistration userRegistration = new UIUserRegistrationImpl();
        maintenance.registerUser(userRegistration);
        User user = registeredUserDetails.getUser("baeldung@baeldung.com");
        System.out.println(user);
        System.out.println(user.getUserType());
    }
}
