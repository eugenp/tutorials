package com.baeldung.architecture.core.adapters;

import com.baeldung.architecture.core.model.EnergyBlock;

public interface EnergyConsumptionAddEvent {
    void addEnergyConsumption(EnergyBlock energyBlock);
}
