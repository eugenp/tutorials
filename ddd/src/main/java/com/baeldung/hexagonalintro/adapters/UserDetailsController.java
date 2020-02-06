package com.baeldung.hexagonalintro.adapters;

import com.baeldung.hexagonalintro.core.UserDetails;
import com.baeldung.hexagonalintro.ports.UpdatePasswordPort;
import com.baeldung.hexagonalintro.ports.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
class UserDetailsController implements UpdatePasswordPort {

    private final UserDetails userDetails;

    @PutMapping("/v1/user-details")
    @ResponseStatus(NO_CONTENT)
    @Override
    public void updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        String username = updatePasswordRequest.getUsername();
        String oldPassword = updatePasswordRequest.getOldPassword();
        String newPassword = updatePasswordRequest.getNewPassword();
        userDetails.updatePassword(username, oldPassword, newPassword);
    }
}
