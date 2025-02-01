package com.baeldung.gcp.firebase.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baeldung.gcp.firebase.auth.FirebaseAuthClient.FirebaseSignInResponse;
import com.baeldung.gcp.firebase.auth.FirebaseAuthClient.RefreshTokenResponse;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final FirebaseAuthClient firebaseAuthClient;

    public UserController(UserService userService, FirebaseAuthClient firebaseAuthClient) {
        this.userService = userService;
        this.firebaseAuthClient = firebaseAuthClient;
    }

    @GetMapping
    public ResponseEntity<UserRecord> getUser() throws FirebaseAuthException {
        UserRecord userRecord = userService.retrieve();
        return ResponseEntity.ok(userRecord);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) throws FirebaseAuthException {
        userService.create(request.emailId(), request.password());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<FirebaseSignInResponse> loginUser(@RequestBody LoginUserRequest request) {
        FirebaseSignInResponse response = firebaseAuthClient.login(request.emailId(), request.password());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        RefreshTokenResponse response = firebaseAuthClient.exchangeRefreshToken(request.refreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() throws FirebaseAuthException {
        userService.logout();
        return ResponseEntity.ok().build();
    }

    record CreateUserRequest(String emailId, String password) {}

    record LoginUserRequest(String emailId, String password) {}

    record RefreshTokenRequest(String refreshToken) {}

}