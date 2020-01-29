package com.baeldung.hexagonpattern.domain;

public class LibraryMaterialType {

	private String item;

	public String getItem() {
		return item;
	}

	public void setItem(String item) throws Exception {
		if (LibraryMaterial.valueOf(item) != null) {
			this.item = item;
		} else {
			throw new Exception("Library Material not valid");
		}
	}

}
