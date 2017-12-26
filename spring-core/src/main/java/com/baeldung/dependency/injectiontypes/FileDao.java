package com.baeldung.dependency.injectiontypes;

class FileDao implements Dao {

	@Override
	public String save(String customerName) {
		return customerName;
	}

}
