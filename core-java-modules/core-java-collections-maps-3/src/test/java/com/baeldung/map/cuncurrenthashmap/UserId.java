package com.baeldung.map.cuncurrenthashmap;

public class UserId { 

	private int id;

	public UserId(int id) {
		super();
		this.id = id;
	}

	@Override
	public int hashCode() {
		return this.id%10;
	}

}
