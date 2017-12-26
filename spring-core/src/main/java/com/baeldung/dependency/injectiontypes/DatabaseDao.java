package com.baeldung.dependency.injectiontypes;

class DatabaseDao implements Dao {

	@Override
	public String save(String customerName) {
		return customerName;
	}

}
