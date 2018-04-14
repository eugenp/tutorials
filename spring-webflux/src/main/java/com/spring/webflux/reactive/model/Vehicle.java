package com.spring.webflux.reactive.model;

import java.io.Serializable;

public class Vehicle implements Serializable {
    
    private static final long serialVersionUID = -6415713305955411683L;
    private String  carPlateNumber;
    private Long    weight;
    private Integer speed;
    private String  color;
    private Integer modelYear;
    private String  gasType;
    
    public Vehicle() {
    	
    }
    public Vehicle(String carPlateNumber, Long weight, Integer speed, String color, Integer modelYear, String gasType) {
        super();
        this.carPlateNumber = carPlateNumber;
        this.weight    = weight;
        this.speed     = speed;
        this.color     = color;
        this.modelYear = modelYear;
        this.gasType   = gasType;
    }
    public String getCarPlateNumber() {
        return carPlateNumber;
    }
    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }
    public Long getWeight() {
        return weight;
    }
    public void setWeight(Long weight) {
        this.weight = weight;
    }
    public Integer getSpeed() {
        return speed;
    }
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
    public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getModelYear() {
		return modelYear;
	}
	public void setModelYear(Integer modelYear) {
		this.modelYear = modelYear;
	}
	public String getGasType() {
		return gasType;
	}
	public void setGasType(String gasType) {
		this.gasType = gasType;
	}
	@Override
	public String toString() {
		return String.format("Vehicle [carPlateNumber=%s, weight=%s, speed=%s, color=%s, modelYear=%s, gasType=%s]",
				carPlateNumber, weight, speed, color, modelYear, gasType);
	}
	
}
