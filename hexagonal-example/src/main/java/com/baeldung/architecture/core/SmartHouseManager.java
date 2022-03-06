package com.baeldung.architecture.core;


import com.baeldung.architecture.core.adapters.EnergyConsumptionAddEvent;
import com.baeldung.architecture.core.model.EnergyBlock;
import com.baeldung.architecture.core.ports.EnergyRepository;
import com.baeldung.architecture.core.ports.ExcessiveEnergyNotifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SmartHouseManager implements EnergyConsumptionAddEvent {
    private static final int MAXIMUM_DAILY_CONSUMPTION = 1000;
    private final ExcessiveEnergyNotifier excessiveEnergyNotifier;
    private final EnergyRepository repository;

    public SmartHouseManager(ExcessiveEnergyNotifier excessiveEnergyNotifier, EnergyRepository repository) {
        this.excessiveEnergyNotifier = excessiveEnergyNotifier;
        this.repository = repository;
    }

    @Override
    public void addEnergyConsumption(EnergyBlock energyBlock) {
        repository.saveEnergyBlock(energyBlock);

        repository.fetchEnergyBlocksByDate(new Date()).stream()
                .map(EnergyBlock::getEnergyInKiloWatt)
                .reduce(Integer::sum)
                .filter(sumValue -> sumValue > MAXIMUM_DAILY_CONSUMPTION)
                .ifPresent(excessiveEnergyNotifier::alertExcessiveEnergyUse);
    }
}
