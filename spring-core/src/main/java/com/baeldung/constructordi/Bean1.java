package com.baeldung.constructordi;

public class Bean1 {

	private Bean2 bean2;

	public Bean1(Bean2 bean2) {
		this.bean2 = bean2;
	}

	public String toString() {
		return bean2.toString();
	}
}