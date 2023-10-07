package com.baeldung.dto;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import com.baeldung.deserializer.BooleanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class BooleanObject {

    @NotNull(message = "boolField cannot be null")
    Boolean boolField;

    @AssertTrue(message = "trueField must have true value")
    Boolean trueField;

    @NotNull(message = "falseField cannot be null")
    @AssertFalse(message = "falseField must have false value")
    Boolean falseField;

    @JsonDeserialize(using = BooleanDeserializer.class)
    Boolean boolStringVar;

    public Boolean getBoolField() {
        return boolField;
    }

    public void setBoolField(Boolean boolField) {
        this.boolField = boolField;
    }

    public Boolean getTrueField() {
        return trueField;
    }

    public void setTrueField(Boolean trueField) {
        this.trueField = trueField;
    }

    public Boolean getFalseField() {
        return falseField;
    }

    public void setFalseField(Boolean falseField) {
        this.falseField = falseField;
    }

    public Boolean getBoolStringVar() {
        return boolStringVar;
    }

    public void setBoolStringVar(Boolean boolStringVar) {
        this.boolStringVar = boolStringVar;
    }
}
