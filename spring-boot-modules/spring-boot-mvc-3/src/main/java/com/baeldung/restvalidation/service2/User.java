package com.baeldung.restvalidation.service2;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotEmpty(message = "{validation.email.notEmpty}")
    private String email;

}