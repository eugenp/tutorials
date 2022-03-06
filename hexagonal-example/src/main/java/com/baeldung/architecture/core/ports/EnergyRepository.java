package com.baeldung.architecture.core.ports;

import com.baeldung.architecture.core.model.EnergyBlock;

import java.util.Date;
import java.util.List;

public interface EnergyRepository {

    void saveEnergyBlock(EnergyBlock energyBlock);

    List<EnergyBlock> fetchEnergyBlocksByDate(Date date);
}
