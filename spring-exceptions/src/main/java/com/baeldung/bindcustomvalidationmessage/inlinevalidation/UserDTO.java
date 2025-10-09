package com.baeldung.bindcustomvalidationmessage.inlinevalidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotNull(message = "User name must not be null")
    @Size(min = 2, message = "User name must be at least 2 characters long")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;
}
