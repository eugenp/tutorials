package com.baeldung.architecture.rest;


import com.baeldung.architecture.core.adapters.EnergyConsumptionAddEvent;
import com.baeldung.architecture.core.model.EnergyBlock;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
public class EnergyBlockEventController {

    private final EnergyConsumptionAddEvent energyConsumptionAddEvent;

    public EnergyBlockEventController(EnergyConsumptionAddEvent energyConsumptionAddEvent) {
        this.energyConsumptionAddEvent = energyConsumptionAddEvent;
    }

    @PostMapping("/addEnergyConsumption/{smartdDeviceId}/{energyConsumptionInKw}")
    public void addEnergyConsumption(@PathVariable String smartDeviceId, @PathVariable int energyConsumptionInKw) {
        if (smartDeviceId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "smartDeviceId Cannot Be Null");
        }
        EnergyBlock energyBlock = EnergyBlock.builder()
                .smartDeviceId(smartDeviceId)
                .energyInKiloWatt(energyConsumptionInKw)
                .eventDate(new Date()).build();

        energyConsumptionAddEvent.addEnergyConsumption(energyBlock);
    }
}
