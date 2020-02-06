package com.baeldung.hexagonalintro.adapters;

import com.baeldung.hexagonalintro.core.UserDetails;
import com.baeldung.hexagonalintro.ports.UpdatePasswordRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeldung.hexagonalintro.adapters.UserDetailsControllerUnitTest.Fixture.newPassword;
import static com.baeldung.hexagonalintro.adapters.UserDetailsControllerUnitTest.Fixture.oldPassword;
import static com.baeldung.hexagonalintro.adapters.UserDetailsControllerUnitTest.Fixture.username;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDetailsControllerUnitTest {

    @InjectMocks
    private UserDetailsController userDetailsController;

    @Mock
    private UserDetails userDetails;

    @Test
    void whenUpdatingThePassword_thenItShouldDelegateToHaveItUpdated() {
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(username, oldPassword, newPassword);
        userDetailsController.updatePassword(updatePasswordRequest);
        verify(userDetails).updatePassword(username, oldPassword, newPassword);
    }

    interface Fixture {
        String username = "username";

        String oldPassword = "oldPassword";

        String newPassword = "newPassword";
    }
}
