package org.baeldung;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        System.setProperty("java.security.policy", "file:./server.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "org.baeldung.RandomNumberGenerator";
            RandomNumberGenerator randomNumberGenerator = new RandomNumberGeneratorEngine();
            RandomNumberGenerator stub =
                    (RandomNumberGenerator) UnicastRemoteObject.exportObject(randomNumberGenerator, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("org.baeldung.RandomNumberGenerator bound");
        } catch (Exception e) {
            System.err.println("org.baeldung.RandomNumberGenerator exception:");
            e.printStackTrace();
        }
    }
}
