package com.baeldung.hexagonal.port;

public interface UserApiPort {

    String getLoginToken(String username, String password);
}
