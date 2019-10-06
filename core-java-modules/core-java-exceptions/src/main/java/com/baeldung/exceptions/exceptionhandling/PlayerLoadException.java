package com.baeldung.exceptions.exceptionhandling;

import java.io.IOException;

public class PlayerLoadException extends Exception {

    public PlayerLoadException(IOException io) {
        super(io);
    }

}
