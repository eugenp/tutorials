package com.baeldung.hexagonalarch.logic;

import java.time.LocalDateTime;

public class Rent {
	
	private LocalDateTime rentTime;
	
	private Car car;
	
	private Client client;
	
	private String code;
	
	public Rent (LocalDateTime rentTime,Car car,Client client,String code) {
		this.rentTime=rentTime;
		this.car=car;
		this.client=client;
		this.code=code;
	}

	public LocalDateTime getRentTime() {
		return rentTime;
	}

	public void setRentTime(LocalDateTime rentTime) {
		this.rentTime = rentTime;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
