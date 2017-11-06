package org.baeldung;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) throws RemoteException {
        System.setProperty("java.security.policy", "file:./server.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        String name = "RandomNumberGenerator";
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGeneratorEngine();
        RandomNumberGenerator stub =
                (RandomNumberGenerator) UnicastRemoteObject.exportObject(randomNumberGenerator, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind(name, stub);
        System.out.println("RandomNumberGenerator bound");
    }
}
