package com.example.springwebflux.events;

import java.util.Date;

public class Train {
	private String trainName;
	private Date trainTime;

	public Train(String trainName, Date trainTime) {
		this.trainName = trainName;
		this.trainTime = trainTime;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public Date getTrainTime() {
		return trainTime;
	}

	public void setTrainTime(Date trainTime) {
		this.trainTime = trainTime;
	}

}
