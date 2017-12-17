package com.baeldung.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.spring.di.config.AppConfig;
import com.baeldung.spring.di.vehicle.Vehicle;

public class SpringDIExample {
   public static void main(String[] args) {
	
      @SuppressWarnings("resource")
      ApplicationContext context2=new AnnotationConfigApplicationContext(AppConfig.class);
      Vehicle vehicleData = context2.getBean(Vehicle.class);
      vehicleData.printVehicleDataFieldDI();
      vehicleData.getVehiclePartsSetterDI();
   }
}
