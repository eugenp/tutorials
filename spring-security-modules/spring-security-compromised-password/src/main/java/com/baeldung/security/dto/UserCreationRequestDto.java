package com.baeldung.security.dto;

import com.baeldung.security.validation.NotCompromised;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequestDto {

    @NotBlank(message = "EmailId must not be empty")
    @Email(message = "EmailId must be of valid format")
    private String emailId;

    @NotCompromised
    @NotBlank(message = "Password must not be empty")
    private String password;

}
