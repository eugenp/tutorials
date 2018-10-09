package com.baeldung.hexagonal.architecture;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Car {
	String manufacturerName;
	String fuleType;
	String modelNo;
	String yearOfManufacture;
	String vehicleType;
	int noOfGears;
	
	public void startCar() {
		//Start the car
	}
	
	public void stopCar() {
		//Stop the car
	}
	
	public void changeGear(int gearNo){
		//Change gear
	}
	
	public void openBoot() {
		//Open boot of the car
	}
	
	public void enableChildLock() {
		//Enable child lock in the car
	}

}
