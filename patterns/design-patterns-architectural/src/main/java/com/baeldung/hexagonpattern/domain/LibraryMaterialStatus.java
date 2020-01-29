package com.baeldung.hexagonpattern.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LibraryMaterialStatus {

	private String materialStatus;
	private LocalDate dueDate;

	@Override
	public String toString() {
		return "LibraryMaterialStatus [materialStatus=" + materialStatus + ", dueDate=" + dueDate + "]";
	}

}
