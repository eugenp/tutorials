package com.baeldung.hexagonalintro.adapters;

import lombok.Value;

@Value
class UpdatePasswordRequest {

    String username;

    String oldPassword;

    String newPassword;
}
