package org.baeldung.bean.injection;

import org.springframework.beans.factory.annotation.Autowired;

public class Car {

	@Autowired
	private Wheel wheel;

	public Car() {
		wheel = new Wheel();
	}

	public Car(Wheel wheel) {
		this.wheel = wheel;
	}

	@Autowired
	public void setWheel(Wheel wheel) {
		this.wheel = wheel;
	}

	public Wheel getWheel() {
		return this.wheel;
	}
}
