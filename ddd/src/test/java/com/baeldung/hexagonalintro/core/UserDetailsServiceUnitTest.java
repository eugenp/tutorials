package com.baeldung.hexagonalintro.core;

import com.baeldung.hexagonalintro.ports.UserDetailsPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.baeldung.hexagonalintro.core.UserDetailsServiceUnitTest.Fixture.newPassword;
import static com.baeldung.hexagonalintro.core.UserDetailsServiceUnitTest.Fixture.oldPassword;
import static com.baeldung.hexagonalintro.core.UserDetailsServiceUnitTest.Fixture.username;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceUnitTest {

    @InjectMocks
    private UserDetailsService userDetailsService;

    @Mock
    private UserDetailsPort userDetailsPort;

    @Test
    void t() {
        given(userDetailsPort.isCorrectPassword(username, oldPassword)).willReturn(false);

        userDetailsService.updatePassword(username, oldPassword, newPassword);

        verify(userDetailsPort, never()).updatePassword(anyString(), anyString());
    }

    @Test
    void t2() {
        given(userDetailsPort.isCorrectPassword(username, oldPassword)).willReturn(true);

        userDetailsService.updatePassword(username, oldPassword, newPassword);

        verify(userDetailsPort).updatePassword(username, newPassword);
    }

    interface Fixture {
        String username = "username";

        String oldPassword = "oldPassword";

        String newPassword = "newPassword";
    }
}
