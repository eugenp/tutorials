package com.baeldung.kogito.rules.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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
