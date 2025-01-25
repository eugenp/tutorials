package com.baeldung.exceptions.throwvsthrows;

import java.net.SocketException;

import com.sun.mail.iap.ConnectionException;

public class TryCatch {

    public void execute() throws SocketException, ConnectionException, Exception  {
        //code that would throw any of: SocketException, ConnectionException, Exception
    }

}
