package com.baeldung.beaninjectiontypes.javaconfigbased.setterdomain;

public class Locality {
	private String houseNo;
	private String road;

	public Locality() {
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	@Override
	public String toString() {
		return "Locality [houseNo=" + houseNo + ", road=" + road + "]";
	}

}