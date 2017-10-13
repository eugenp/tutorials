package org.baeldung;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        System.setProperty("java.security.policy", "file:./client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "org.baeldung.RandomNumberGenerator";
            Registry registry = LocateRegistry.getRegistry();
            RandomNumberGenerator randomNumberGenerator = (RandomNumberGenerator) registry.lookup(name);
            int number = randomNumberGenerator.get();
            System.out.println(number);
        } catch (Exception e) {
            System.err.println("org.baeldung.RandomNumberGenerator exception:");
            e.printStackTrace();
        }
    }
}
