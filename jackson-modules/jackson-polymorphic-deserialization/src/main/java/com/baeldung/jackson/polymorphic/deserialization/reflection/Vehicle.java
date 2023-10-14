package com.baeldung.jackson.polymorphic.deserialization.reflection;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
public class Vehicle {

    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @VehicleSubType("ELECTRIC_VEHICLE")
    public static class ElectricVehicle extends Vehicle {

        String autonomy;
        String chargingTime;

        public String getAutonomy() {
            return autonomy;
        }

        public void setAutonomy(String autonomy) {
            this.autonomy = autonomy;
        }

        public String getChargingTime() {
            return chargingTime;
        }

        public void setChargingTime(String chargingTime) {
            this.chargingTime = chargingTime;
        }
    }

    @VehicleSubType("FUEL_VEHICLE")
    public static class FuelVehicle extends Vehicle {

        String fuelType;
        String transmissionType;
    }
}
