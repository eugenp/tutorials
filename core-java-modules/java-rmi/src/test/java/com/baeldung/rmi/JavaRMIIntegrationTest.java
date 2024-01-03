package com.baeldung.rmi;

import org.junit.Before;
import org.junit.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JavaRMIIntegrationTest {

    private MessengerServiceImpl messengerService;

    @Before
    public void init() {
        try {
            messengerService = new MessengerServiceImpl();
            messengerService.createStubAndBind();
        } catch (RemoteException e) {
            fail("Exception Occurred: " + e);
        }
    }

    @Test
    public void whenClientSendsMessageToServer_thenServerSendsResponseMessage() {
        try {
            Registry registry = LocateRegistry.getRegistry();
            MessengerService server = (MessengerService) registry.lookup("MessengerService");
            String responseMessage = server.sendMessage("Client Message");

            String expectedMessage = "Server Message";
            assertEquals(responseMessage, expectedMessage);
        } catch (RemoteException | NotBoundException e) {
            fail("Exception Occurred: " + e);
        }
    }

}