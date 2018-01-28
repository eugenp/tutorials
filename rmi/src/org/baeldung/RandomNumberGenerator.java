package org.baeldung;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RandomNumberGenerator extends Remote{
    int get() throws RemoteException;
}
