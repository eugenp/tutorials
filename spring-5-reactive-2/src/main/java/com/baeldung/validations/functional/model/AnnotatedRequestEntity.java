package com.baeldung.validations.functional.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnnotatedRequestEntity {
    @NotNull
    private String user;

    @NotNull
    @Size(min = 4, max = 7)
    private String password;

}
