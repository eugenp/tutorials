package com.baeldung.jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

/**
 * REST APIs that contain all the operations that can be performed on a User
 */
@RequestMapping("/api/user")
@RestController
@Tag(name = "User", description = "The User API. Contains all the operations that can be performed on a user.")
public class UserApi {

    private final UserDetailsService userDetailsService;

    public UserApi(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * API to get the current user. Returns the user details for the provided JWT token
     * @param authentication The authentication object that contains the JWT token
     * @return Returns the user details for the provided JWT token
     */
    @Operation(summary = "Get user details", description = "Get the user details. The operation returns the details of the user that is associated " + "with the provided JWT token.")
    @GetMapping
    public UserDetails getUser(Authentication authentication) {
        return userDetailsService.loadUserByUsername(authentication.getName());
    }

    /**
     * API to delete the current user.
     * @param authentication The authentication object that contains the JWT token
     * @return Returns a success message on deletion of the user
     */
    @Operation(summary = "Delete user details", description = "Delete user details. The operation deletes the details of the user that is " + "associated with the provided JWT token.")
    @DeleteMapping
    public String deleteUser(Authentication authentication) {
        return MessageFormat.format("User {0} deleted successfully", authentication.getName());
    }
}
