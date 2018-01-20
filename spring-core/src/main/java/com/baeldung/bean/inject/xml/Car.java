package com.baeldung.bean.inject.xml;

public class Car {

	private Wheel wheel;
	@SuppressWarnings("unused")
	private AudioSystem audioSystem;

	public Car(Wheel wheel) {
		this.wheel = wheel;
	}

	public void setAudioSystem(AudioSystem audioSystem) {
		this.audioSystem = audioSystem;
	}

	public String details() {
		StringBuilder carBuilder = new StringBuilder();
		carBuilder.append("Wheel Type:").append(wheel.getType()).append(", Manufacturer:")
				.append(wheel.getManufacturer());
		if (null != audioSystem) {
			carBuilder.append(" And ").append("Audio Brand Name:").append(audioSystem.getBrandName())
					.append(", Manufacturer:").append(audioSystem.getManufacturer());
		}
		return carBuilder.toString();
	}
}
