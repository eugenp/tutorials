package com.baeldung.architecture.core.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EnergyBlock {
    int id;
    String smartDeviceId;
    int energyInKiloWatt;
    Date eventDate;
}
