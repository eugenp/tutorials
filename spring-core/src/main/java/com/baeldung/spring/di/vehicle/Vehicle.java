package com.baeldung.spring.di.vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.spring.di.parts.VehicleParts;
@Component
public class Vehicle {
 private final Logger logger = LoggerFactory.getLogger(Vehicle.class);
	@Autowired
	private VehicleParts vehiclePartsFieldDI;
	
	private VehicleParts vehiclePartsSetterDI;
	
	public void printVehicleDataFieldDI(){
		logger.info("Field Injection {}", vehiclePartsFieldDI);
			}
	
	public Vehicle(VehicleParts vehiclePartsConsDI) {
		logger.info("Constructor Injection {}", vehiclePartsConsDI);
		
	}

	public void getVehiclePartsSetterDI() {
		logger.info("Setter Injection {}", vehiclePartsSetterDI);
	}
	@Autowired
	public void setVehiclePartsSetterDI(VehicleParts vehiclePartsSetterDI) {
		this.vehiclePartsSetterDI = vehiclePartsSetterDI;
	}
	
	
}
