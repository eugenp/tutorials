package org.baeldung;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.setProperty("java.security.policy", "file:./client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        String name = "RandomNumberGenerator";
        Registry registry = LocateRegistry.getRegistry();
        RandomNumberGenerator randomNumberGenerator = (RandomNumberGenerator) registry.lookup(name);
        int number = randomNumberGenerator.get();
        System.out.println("Received random number:" + number);
    }
}
