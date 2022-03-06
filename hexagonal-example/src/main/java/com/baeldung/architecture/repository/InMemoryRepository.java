package com.baeldung.architecture.repository;

import com.baeldung.architecture.core.model.EnergyBlock;
import com.baeldung.architecture.core.ports.EnergyRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class InMemoryRepository implements EnergyRepository {

    ConcurrentHashMap<Integer, EnergyBlock> energyConsumptionMap = new ConcurrentHashMap<>();

    @Override
    public void saveEnergyBlock(EnergyBlock energyBlock) {
        energyConsumptionMap.put(energyBlock.getId(), energyBlock);
    }

    @Override
    public List<EnergyBlock> fetchEnergyBlocksByDate(Date date) {
        return energyConsumptionMap.values().stream()
                .filter(energyBlock -> isSameDay(date, new Date()))
                .collect(Collectors.toList());
    }

    public static boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDate2 = date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate1.isEqual(localDate2);
    }
}
