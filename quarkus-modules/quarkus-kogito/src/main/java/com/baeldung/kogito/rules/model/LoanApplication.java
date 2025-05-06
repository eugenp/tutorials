package com.baeldung.kogito.rules.model;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanApplication {

    @NotNull
    private UUID id;
    @NotNull
    @Valid
    private Person person;
    @NotNull
    @Valid
    private Conditions conditions;
    private Decision decision;

}
