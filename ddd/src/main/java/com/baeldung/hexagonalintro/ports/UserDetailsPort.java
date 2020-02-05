package com.baeldung.hexagonalintro.ports;

public interface UserDetailsPort {

    boolean isCorrectPassword(String username, String newPassword);

    void updatePassword(String username, String newPassword);
}
