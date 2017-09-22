package com.baeldung.setterdi;
public class Bean1 {

	private Bean2 bean2;

	public void setBean2(Bean2 bean2) {
		this.bean2 = bean2;
	}

	public String toString() {
		return bean2.toString();
	}
}