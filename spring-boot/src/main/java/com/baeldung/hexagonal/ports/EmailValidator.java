package com.baeldung.hexagonal.ports;

public interface EmailValidator {

    boolean validate(String emailAddress);

}
