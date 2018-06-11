package com.example.ehidiamen.traindemo.model;

public class TrainArrival {

	private String id;
	private String arrivalTime;
	public TrainArrival(String id, String arrivalTime) {
		super();
		this.id = id;
		this.arrivalTime = arrivalTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	
}
