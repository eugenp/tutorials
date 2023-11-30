package com.baeldung.restvalidation.service2;

import javax.validation.constraints.NotEmpty;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotEmpty(message = "{validation.email.notEmpty}")
    private String email;

}