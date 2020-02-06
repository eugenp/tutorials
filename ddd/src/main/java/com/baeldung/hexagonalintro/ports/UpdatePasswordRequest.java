package com.baeldung.hexagonalintro.ports;

import lombok.Value;

@Value
public class UpdatePasswordRequest {

    String username;

    String oldPassword;

    String newPassword;
}
