package com.baeldung.tutorial.hexagonal.core;

import com.baeldung.tutorial.hexagonal.port.out.RegisteredUserDetails;
import com.baeldung.tutorial.hexagonal.port.out.UserRepository;
import lombok.Setter;

/**
 * This is the out port implementation where user details are fetched from repo and give to the application.
 * This class doesn't bother on from which repo the data is being fetched as it would be taken care outside of repo by different underlying adapters.
 */
public class RegisteredUserDetailsImpl implements RegisteredUserDetails {

    @Setter
    private UserMaintenance userMaintenance;

    @Override
    public User getUser(String emailId) {
        return userMaintenance.getUser(emailId);
    }

    public Long getUserUnqiueId(String emailId) {
        return userMaintenance.getUser(emailId).getUniqueId();
    }

    public UserType getUserType(String emailId) {
        return userMaintenance.getUser(emailId).getUserType();
    }
}
