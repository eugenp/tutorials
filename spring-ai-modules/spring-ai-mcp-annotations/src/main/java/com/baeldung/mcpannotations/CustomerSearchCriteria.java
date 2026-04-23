package com.baeldung.mcpannotations;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerSearchCriteria(
        String region,
        boolean activeOnly,
        @JsonProperty(required = false) Integer limit
) {}
