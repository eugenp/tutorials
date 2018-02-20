package com.mala.model;

public class Patient {
	private IMedicine medicine;
	
	public Patient() {}
	
	public Patient(IMedicine medicine) {
		this.medicine = medicine;
	}
	
	public IMedicine getMedicine() {
		return medicine;
	}
	
	public void setMedicine(IMedicine medicine) {
		this.medicine = medicine;
	}
	
	public String toString() {
		return medicine.toString();
	}
}
