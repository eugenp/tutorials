package com.baeldung.hexagonal.core.service;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.hexagonal.core.model.ExchangeRate;
import com.baeldung.hexagonal.core.ports.ExchangeRateRepository;
import com.baeldung.hexagonal.core.ports.ExchangeRateRetrievalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRetrievalService retrievalService;
    private final ExchangeRateRepository<ExchangeRate> repository;

    @Override
    public UpdateResult update(Currency src, Currency dest) {
        List<ExchangeRate> retrieved = retrievalService.retrieve(LocalDate.of(2018, 10, 1), src, dest);
        if (!retrieved.isEmpty()) {
            repository.save(retrieved);
            return new UpdateResult(UpdateResult.Status.OK, "Everything OK!");
        }
        return new UpdateResult(UpdateResult.Status.ERROR, "Unknown error");
    }

    @Override
    public List<ExchangeRate> getHistory(LocalDate from, LocalDate to, Currency src, Currency dest) {
        return repository.retrieve(from, to, src, dest);
    }
}
