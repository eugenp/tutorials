package com.baeldung.socket;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FindAvailablePortUnitTest {

    private static final int FREE_PORT_NUMBER = 8082;

    @Test
    public void givenExplicitFreePort_whenCreatingServer_thenPortIsAssigned() {
        try {
            ServerSocket serverSocket = new ServerSocket(FREE_PORT_NUMBER);
            assertThat(serverSocket).isNotNull();
            assertThat(serverSocket.getLocalPort()).isEqualTo(FREE_PORT_NUMBER);
        } catch (IOException e) {
            fail("Port is not available");
        }
    }

    @Test
    public void givenExplicitOccupiedPort_whenCreatingServer_thenExceptionIsThrown() {
        try {
            new ServerSocket(FREE_PORT_NUMBER);
            new ServerSocket(FREE_PORT_NUMBER);
            fail("Same port cannot be used twice");
        } catch (IOException e) {
            assertThat(e).hasMessageContaining("Address already in use: bind");
        }
    }

}
