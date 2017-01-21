package com.java.test.externalizable;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;


public class Employee implements Externalizable {
	private static final long serialVersionUID = 1234343434343434L;
	int id;
	String name;
	String address;

	public int getId() {
		return id;
	}
        
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
	    this.id = in.readInt();
	    this.name=(String) in.readObject();
	    this.address=(String) in.readObject();
	}
	
	public void writeExternal(ObjectOutput out) throws IOException {
	    out.writeInt(id);
	    out.writeObject(name);
	    out.writeObject(address);

	}
	
	public String toString() {
		return "Id :"+id+" Name :"+name+" Address :"+address;
	}

}
