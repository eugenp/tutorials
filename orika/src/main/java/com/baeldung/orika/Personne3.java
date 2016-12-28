package com.baeldung.orika;

public class Personne3 {
	private String name;
	private long dtob;

	public Personne3() {

	}

	public Personne3(String name, long dtob) {
		this.name = name;
		this.dtob = dtob;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDtob() {
		return dtob;
	}

	public void setDtob(long dtob) {
		this.dtob = dtob;
	}

	@Override
	public String toString() {
		return "Personne3 [name=" + name + ", dtob=" + dtob + "]";
	}

}
