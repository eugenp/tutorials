package com.baeldung.boot.hexagonal.app;

import com.baeldung.boot.hexagonal.command.DiscountCommand;

public interface DiscounterApi {
    double discount(DiscountCommand command);
}
