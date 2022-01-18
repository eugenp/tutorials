package com.baeldung.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient Address address; // not an serializable object
	private Person person;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(address.getHouseNumber());
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		Integer houseNumber = (Integer) ois.readObject();
		Address a = new Address();
		a.setHouseNumber(houseNumber);
		this.setAddress(a);
	}

}
