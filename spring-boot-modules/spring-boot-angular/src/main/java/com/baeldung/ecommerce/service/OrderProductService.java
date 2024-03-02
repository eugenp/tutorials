package com.baeldung.ecommerce.service;

import org.springframework.validation.annotation.Validated;

import com.baeldung.ecommerce.model.OrderProduct;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
public interface OrderProductService {

    OrderProduct create(@NotNull(message = "The products for order cannot be null.") @Valid OrderProduct orderProduct);
}
