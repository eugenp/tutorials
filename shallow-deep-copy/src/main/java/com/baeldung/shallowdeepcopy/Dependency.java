package com.baeldung.shallowdeepcopy;

public class Dependency implements Cloneable {
	private int value = 13;
	
	public Dependency(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
