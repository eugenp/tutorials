package com.baeldung.exceptions.exceptionhandling;

public class PlayerScoreException extends Exception {

    public PlayerScoreException(Exception e) {
        super(e);
    }
}
