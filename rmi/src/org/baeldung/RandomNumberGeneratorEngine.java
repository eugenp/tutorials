package org.baeldung;

import java.rmi.RemoteException;

public class RandomNumberGeneratorEngine implements RandomNumberGenerator {
    @Override
    public int get() throws RemoteException {
        return (int) (100 * Math.random());
    }
}
