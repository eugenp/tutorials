package com.baeldung.jackson.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public class MyPair {

	private String first;
	private String second;

	public MyPair(String first, String second) {
		this.first = first;
		this.second = second;
	}

	public MyPair(String both) {
		String[] pairs = both.split("and");
		this.first = pairs[0].trim();
		this.second = pairs[1].trim();
	}

	@Override
	@JsonValue
	public String toString() {
		return first + " and " + second;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}
}