package com.baeldung.socket;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.junit.jupiter.api.Test;
import org.springframework.util.SocketUtils;

import java.io.IOException;
import java.net.ServerSocket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class FindFreePortUnitTest {

    private static final int DEFAULT_RANDOM_PORT = 34307;

    @Test
    public void givenExplicitFreePort_whenCreatingServerSocket_thenThatPortIsAssigned() {
        int freePort = getFreePort();

        try (ServerSocket serverSocket = new ServerSocket(freePort)) {
            assertThat(serverSocket).isNotNull();
            assertThat(serverSocket.getLocalPort()).isEqualTo(freePort);
        } catch (IOException e) {
            fail("Port is not available");
        }
    }

    @Test
    public void givenExplicitOccupiedPort_whenCreatingServerSocket_thenExceptionIsThrown() {
        int freePort = getFreePort();

        try (ServerSocket serverSocket = new ServerSocket(freePort)) {
            new ServerSocket(freePort);
            fail("Same port cannot be used twice");
        } catch (IOException e) {
            assertThat(e).hasMessageContaining("Address already in use");
        }
    }

    @Test
    public void givenExplicitPortRange_whenCreatingServerSocket_thenOnePortIsAssigned() {
        for (int port : getFreePorts()) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                assertThat(serverSocket).isNotNull();
                assertThat(serverSocket.getLocalPort()).isEqualTo(port);
                return;
            } catch (IOException e) {
                assertThat(e).hasMessageContaining("Address already in use");
            }
        }
        fail("No free port in the range found");
    }

    @Test
    public void givenPortZero_whenCreatingServerSocket_thenFreePortIsAssigned() {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            assertThat(serverSocket).isNotNull();
            assertThat(serverSocket.getLocalPort()).isGreaterThan(0);
        } catch (IOException e) {
            fail("Port is not available");
        }
    }

    @Test
    public void givenAvailableTcpPort_whenCreatingServerSocket_thenThatPortIsAssigned() {
        int port = SocketUtils.findAvailableTcpPort();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            assertThat(serverSocket).isNotNull();
            assertThat(serverSocket.getLocalPort()).isEqualTo(port);
        } catch (IOException e) {
            fail("Port is not available");
        }
    }

    @Test
    public void givenNoPortDefined_whenCreatingJettyServer_thenFreePortIsAssigned() throws Exception {
        Server jettyServer = new Server();
        ServerConnector serverConnector = new ServerConnector(jettyServer);
        jettyServer.addConnector(serverConnector);
        try {
            jettyServer.start();
            assertThat(serverConnector.getLocalPort()).isGreaterThan(0);
        } catch (Exception e) {
            fail("Failed to start Jetty server");
        } finally {
            jettyServer.stop();
            jettyServer.destroy();
        }
    }

    @Test
    public void givenExplicitFreePort_whenCreatingJettyServer_thenThatPortIsAssigned() throws Exception {
        Server jettyServer = new Server();
        ServerConnector serverConnector = new ServerConnector(jettyServer);
        int freePort = getFreePort();
        serverConnector.setPort(freePort);
        jettyServer.addConnector(serverConnector);
        try {
            jettyServer.start();
            assertThat(serverConnector.getLocalPort()).isEqualTo(freePort);
        } catch (Exception e) {
            fail("Failed to start Jetty server");
        } finally {
            jettyServer.stop();
            jettyServer.destroy();
        }
    }

    @Test
    public void givenPortZero_whenCreatingTomcatServer_thenFreePortIsAssigned() throws Exception {
        Tomcat tomcatServer = new Tomcat();
        tomcatServer.setPort(0);
        try {
            tomcatServer.start();
            assertThat(tomcatServer.getConnector().getLocalPort()).isGreaterThan(0);
        } catch (LifecycleException e) {
            fail("Failed to start Tomcat server");
        } finally {
            tomcatServer.stop();
            tomcatServer.destroy();
        }
    }

    @Test
    public void givenExplicitFreePort_whenCreatingTomcatServer_thenThatPortIsAssigned() throws Exception {
        Tomcat tomcatServer = new Tomcat();
        int freePort = getFreePort();
        tomcatServer.setPort(freePort);
        try {
            tomcatServer.start();
            assertThat(tomcatServer.getConnector().getLocalPort()).isEqualTo(freePort);
        } catch (LifecycleException e) {
            fail("Failed to start Tomcat server");
        } finally {
            tomcatServer.stop();
            tomcatServer.destroy();
        }
    }

    private int[] getFreePorts() {
        int freePort = getFreePort();
        return new int[]{freePort - 1, freePort, freePort + 1};
    }

    private int getFreePort() {
        try(ServerSocket serverSocket = new ServerSocket(0)){
            return serverSocket.getLocalPort();
        } catch (IOException ex){
            return DEFAULT_RANDOM_PORT;
        }
    }
}
