package com.baeldung.hexagonalintro.adapters;

import com.baeldung.hexagonalintro.core.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @PutMapping("/v1/user-details")
    @ResponseStatus(NO_CONTENT)
    void updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        String username = updatePasswordRequest.getUsername();
        String oldPassword = updatePasswordRequest.getOldPassword();
        String newPassword = updatePasswordRequest.getNewPassword();
        userDetailsService.updatePassword(username, oldPassword, newPassword);
    }
}
