package com.baeldung.exceptionhandling;

import java.io.IOException;

public class PlayerLoadException extends Exception {

    public PlayerLoadException(IOException io) {
        super(io);
    }

}
