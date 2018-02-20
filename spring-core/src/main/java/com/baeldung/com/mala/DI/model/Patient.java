package com.mala.DI.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Patient {
	private IMedicine medicine;
	

	public Patient() {}
	
	@Autowired
	public Patient(IMedicine medicine) {
		this.medicine = medicine;
	}
	
	public IMedicine getMedicine() {
		return this.medicine;
	}
	
	public String toString() {
		return medicine.toString();
	}
}
