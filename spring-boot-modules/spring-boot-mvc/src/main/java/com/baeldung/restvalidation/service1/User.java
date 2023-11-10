package com.baeldung.restvalidation.service1;

import javax.validation.constraints.NotEmpty;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotEmpty
    private String email;

}