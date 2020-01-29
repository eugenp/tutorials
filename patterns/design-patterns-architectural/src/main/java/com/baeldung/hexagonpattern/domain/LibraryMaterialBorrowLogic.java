package com.baeldung.hexagonpattern.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class LibraryMaterialBorrowLogic implements Serializable {

	private static final long serialVersionUID = -661356390417382727L;

	public LibraryMaterialStatus processMaterial(List<LibraryMaterialType> items) {
		LibraryMaterialStatus status = new LibraryMaterialStatus();
		try {
			status.setMaterialStatus("extended");
			status.setDueDate(calculateExtensionDate(items));
		} catch (Exception e) {
			status.setMaterialStatus("failed");
		}
		return status;
	}

	private LocalDate calculateExtensionDate(List<LibraryMaterialType> items) throws Exception {
		LocalDate date = LocalDate.now();
		for (LibraryMaterialType type : items) {
			if (type.getItem().equalsIgnoreCase(LibraryMaterial.BOOKS.name())) {
				date = date.plusDays(15);
			} else if (type.getItem().equalsIgnoreCase(LibraryMaterial.MUSIC.name())) {
				date = date.plusDays(5);
			} else if (type.getItem().equalsIgnoreCase(LibraryMaterial.MOVIES.name())) {
				date = date.plusDays(1);
			} else {
				throw new Exception("Invalid Library Material");
			}
		}
		return date;

	}

}
