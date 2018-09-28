package com.baeldung.architecture.hexagon.framework;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baeldung.architecture.hexagon.application.VehicleHandler;

@Controller
public class VehicleController {

	@RequestMapping("/vehicles/{vehicle}/brake")
	public void applyBrake(@PathVariable String vehicle) {
		VehicleHandler vehicleHandler = new VehicleHandler();
		vehicleHandler.applyBrake(vehicle);
	}
}
