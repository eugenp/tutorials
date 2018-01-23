package com.baeldung.bean.inject.boot;

import org.springframework.beans.factory.annotation.Autowired;

public class Car {

	private Wheel wheel;
	private AudioSystem audioSystem;
	private VideoPlayer videoPlayer;

	public Car(Wheel wheel) {
		this.wheel = wheel;
	}

	@Autowired(required = true)
	public void setAudioSystem(AudioSystem audioSystem) {
		this.audioSystem = audioSystem;
	}

	@Autowired(required = false)
	public void setVideoPlayer(VideoPlayer videoPlayer) {
		this.videoPlayer = videoPlayer;
	}

	public String details() {
		StringBuilder carBuilder = new StringBuilder();
		carBuilder.append("Wheel Type:").append(wheel.getType()).append(", Manufacturer:")
				.append(wheel.getManufacturer());
		if (null != audioSystem) {
			carBuilder.append(" And ").append("Audio Brand Name:").append(audioSystem.getBrandName())
					.append(", Manufacturer:").append(audioSystem.getManufacturer());
		}
		if (null != videoPlayer) {
			carBuilder.append(" And ").append("Video Brand Name:").append(videoPlayer.getBrandName())
					.append(", Manufacturer:").append(videoPlayer.getManufacturer());
		}
		return carBuilder.toString();
	}
}
