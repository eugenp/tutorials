package com.baeldung.spring.core;

public class Car {

	private String carName;

	private String carType;

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	@Override
	public String toString() {
		return "Car [carName=" + carName + ", carType=" + carType + "]";
	}

}
