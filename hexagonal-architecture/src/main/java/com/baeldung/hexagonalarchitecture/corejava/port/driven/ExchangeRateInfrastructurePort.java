package com.baeldung.hexagonalarchitecture.corejava.port.driven;

import com.baeldung.hexagonalarchitecture.corejava.domain.ExchangeRate;

import java.util.List;

public interface ExchangeRateInfrastructurePort {
    List<ExchangeRate> loadExchangeRates();
}
