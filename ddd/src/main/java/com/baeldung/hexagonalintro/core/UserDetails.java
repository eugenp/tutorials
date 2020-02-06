package com.baeldung.hexagonalintro.core;

public interface UserDetails {

    void updatePassword(String username, String oldPassword, String newPassword);
}
