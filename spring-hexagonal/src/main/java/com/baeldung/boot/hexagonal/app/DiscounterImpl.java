package com.baeldung.boot.hexagonal.app;

import org.springframework.stereotype.Service;

import com.baeldung.boot.hexagonal.command.DiscountCommand;
import com.baeldung.boot.hexagonal.repository.RateRepository;

@Service
public class DiscounterImpl implements DiscounterApi {
    private final RateRepository rateRepository;

    public DiscounterImpl(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public double discount(DiscountCommand cmd) {
        return cmd.getAmount() * rateRepository.getRate(cmd.getAmount());
    }
}
