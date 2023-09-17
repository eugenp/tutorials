package com.baeldung.dto;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.baeldung.deserializer.BooleanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;

@Data
public class BooleanObject {

    @NotNull(message = "boolField cannot be null")
    Boolean boolField;

    @AssertTrue(message = "trueField must have true value")
    Boolean trueField;

    @AssertFalse(message = "falseField must have false value")
    Boolean falseField;

    @JsonDeserialize(using = BooleanDeserializer.class)
    Boolean boolStringVar;
}
