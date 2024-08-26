package com.baeldung.firebase.auth;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

@Service
public class UserService {

    private static final String DUPLICATE_ACCOUNT_ERROR = "EMAIL_EXISTS";

    private final FirebaseAuth firebaseAuth;

    public UserService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void create(String emailId, String password) {
        CreateRequest request = new CreateRequest();
        request.setEmail(emailId);
        request.setPassword(password);
        request.setEmailVerified(Boolean.TRUE);

        try {
            firebaseAuth.createUser(request);
        } catch (FirebaseAuthException exception) {
            if (exception.getMessage().contains(DUPLICATE_ACCOUNT_ERROR)) {
                throw new AccountAlreadyExistsException("Account with given email-id already exists");
            }
        }
    }

}