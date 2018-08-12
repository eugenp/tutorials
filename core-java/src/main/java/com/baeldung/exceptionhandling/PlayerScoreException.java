package com.baeldung.exceptionhandling;

public class PlayerScoreException extends Exception {

    public PlayerScoreException(Exception e) {
        super(e);
    }
}
