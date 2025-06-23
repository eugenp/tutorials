package com.baeldung.kogito.boundary.model;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanApplicationDto {

    @NotNull
    private UUID id;
    @NotNull
    @Valid
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PersonDto person;
    @NotNull
    @Valid
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ConditionsDto conditions;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DecisionDTO decision;

}
