package com.baeldung.mediator.login;

import com.baeldung.User;
import com.baeldung.mediator.login.contracts.LoginRequest;
import com.baeldung.mediator.login.contracts.LoginResponse;
import com.baeldung.port.UserInterfacePort;
import com.baeldung.port.UserPort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DoLoginTests {

    @InjectMocks
    private DoLoginMediator doLogin;

    @Mock
    private UserPort userPort;
    @Mock
    private UserInterfacePort userInterfacePort;
    @Captor
    private ArgumentCaptor<LoginResponse> captor;

    @Test
    public void when_userAndPassword_thenReturnUser() throws Exception {
        when(userPort.findBy(any(), any()))
                .thenReturn(Optional.of(new User("Baeldung")));

        doLogin.execute(new LoginRequest("baeldung", "123456"),
                userInterfacePort);

        Mockito.verify(userInterfacePort, Mockito.times(1))
                .accept(captor.capture());

        assertEquals("Baeldung", captor.getValue().getName());
    }

    @Test(expected = IllegalStateException.class)
    public void when_userAndPasswordIncorrect_thenThrowIllegalStateException() throws Exception {
        when(userPort.findBy(any(),any()))
                .thenReturn(Optional.empty());

        doLogin.execute(new LoginRequest("baeldung","123456"),
                userInterfacePort);

    }
}
