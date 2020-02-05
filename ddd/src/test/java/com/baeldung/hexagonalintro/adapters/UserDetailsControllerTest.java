package com.baeldung.hexagonalintro.adapters;

import com.baeldung.hexagonalintro.core.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeldung.hexagonalintro.adapters.UserDetailsControllerTest.Fixture.newPassword;
import static com.baeldung.hexagonalintro.adapters.UserDetailsControllerTest.Fixture.oldPassword;
import static com.baeldung.hexagonalintro.adapters.UserDetailsControllerTest.Fixture.username;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDetailsControllerTest {

    @InjectMocks
    private UserDetailsController userDetailsController;

    @Mock
    private UserDetailsService userDetailsService;

    @Test
    void whenUpdatingThePassword_thenItShouldDelegateToHaveItUpdated() {
        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(username, oldPassword, newPassword);
        userDetailsController.updatePassword(updatePasswordRequest);
        verify(userDetailsService).updatePassword(username, oldPassword, newPassword);
    }

    interface Fixture {
        String username = "username";

        String oldPassword = "oldPassword";

        String newPassword = "newPassword";
    }
}
