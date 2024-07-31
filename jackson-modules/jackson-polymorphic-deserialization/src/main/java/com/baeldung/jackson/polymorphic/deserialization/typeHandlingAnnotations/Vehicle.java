package com.baeldung.jackson.polymorphic.deserialization.typeHandlingAnnotations;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Vehicle.ElectricVehicle.class, name = "ELECTRIC_VEHICLE"),
    @JsonSubTypes.Type(value = Vehicle.FuelVehicle.class, name = "FUEL_VEHICLE")
})
public class Vehicle {

    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public static class FuelVehicle extends Vehicle {

        String fuelType;
        String transmissionType;

        public String getFuelType() {
            return fuelType;
        }

        public void setFuelType(String fuelType) {
            this.fuelType = fuelType;
        }

        public String getTransmissionType() {
            return transmissionType;
        }

        public void setTransmissionType(String transmissionType) {
            this.transmissionType = transmissionType;
        }
    }
}
