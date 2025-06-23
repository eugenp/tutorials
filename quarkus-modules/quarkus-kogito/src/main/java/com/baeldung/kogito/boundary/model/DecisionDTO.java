package com.baeldung.kogito.boundary.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DecisionDTO {

    @JsonProperty("approved") APPROVED,
    @JsonProperty("rejected-underage") REJECTED_UNDERAGE,
    @JsonProperty("rejected-not-creditworthy") REJECTED_NOT_CREDITWORTHY

}
