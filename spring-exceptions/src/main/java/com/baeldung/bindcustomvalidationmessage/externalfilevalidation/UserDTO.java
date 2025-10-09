package com.baeldung.bindcustomvalidationmessage.externalfilevalidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotNull(message = "{user.name.notnull}")
    @Size(min = 2, message = "{user.name.size}")
    private String name;

    @NotNull(message = "{user.email.notnull}")
    @Email(message = "{user.email.invalid}")
    private String email;
}
