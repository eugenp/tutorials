package com.baeldung.architecture.core;

import com.baeldung.architecture.core.model.EnergyBlock;
import com.baeldung.architecture.core.ports.EnergyRepository;
import com.baeldung.architecture.core.ports.ExcessiveEnergyNotifier;
import com.baeldung.architecture.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.baeldung.architecture.core.SmartHouseManager.MAXIMUM_DAILY_CONSUMPTION;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SmartHouseManagerTest {


    @Test
    void shouldNotNotifyWhenTotalEnergyIsNotGreaterThanTheThreshold() {
        ExcessiveEnergyNotifier excessiveEnergyNotifier = spy(ExcessiveEnergyNotifier.class);
        EnergyRepository energyRepository = new InMemoryRepository();
        energyRepository.saveEnergyBlock(EnergyBlock.builder().id(1).energyInKiloWatt(MAXIMUM_DAILY_CONSUMPTION - 20).eventDate(new Date()).build());
        SmartHouseManager houseManager = new SmartHouseManager(excessiveEnergyNotifier, energyRepository);
        houseManager.addEnergyConsumption(EnergyBlock.builder().id(2).energyInKiloWatt(10).eventDate(new Date()).build());
        verify(excessiveEnergyNotifier, times(0)).alertExcessiveEnergyUse(anyInt());
    }

    @Test
    void shouldNotifyWhenTotalEnergyIsGreaterThanTheThreshold() {
        ExcessiveEnergyNotifier excessiveEnergyNotifier = spy(ExcessiveEnergyNotifier.class);
        EnergyRepository energyRepository = new InMemoryRepository();
        energyRepository.saveEnergyBlock(EnergyBlock.builder().id(1).energyInKiloWatt(MAXIMUM_DAILY_CONSUMPTION).eventDate(new Date()).build());
        SmartHouseManager houseManager = new SmartHouseManager(excessiveEnergyNotifier, energyRepository);
        houseManager.addEnergyConsumption(EnergyBlock.builder().id(2).energyInKiloWatt(10).eventDate(new Date()).build());
        verify(excessiveEnergyNotifier, times(1)).alertExcessiveEnergyUse(anyInt());
    }
}