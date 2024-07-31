package com.baeldung.problemdetails.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OperationRequest(
    @NotNull(message = "Base price should be greater than zero.") @Positive(message = "Base price should be greater than zero.") Double basePrice,
    @Nullable @Positive(message = "Discount should be greater than zero when provided.") Double discount) {

}
