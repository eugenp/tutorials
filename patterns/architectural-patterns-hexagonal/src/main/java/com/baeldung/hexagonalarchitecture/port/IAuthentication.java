package com.baeldung.hexagonalarchitecture.port;

public interface IAuthentication {
    boolean login(String username, String password);

    boolean register(String username, String password, String email);
}
