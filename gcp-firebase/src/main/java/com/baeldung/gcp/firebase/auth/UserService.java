package com.baeldung.gcp.firebase.auth;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
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

    public UserRecord retrieve() throws FirebaseAuthException {
        String userId = getAuthenticatedUserId();
        return firebaseAuth.getUser(userId);
    }

    private String getAuthenticatedUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .map(Authentication::getPrincipal)
            .filter(String.class::isInstance)
            .map(String.class::cast)
            .orElseThrow(IllegalStateException::new);
    }

}