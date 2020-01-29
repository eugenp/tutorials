package com.baeldung.hexagonpattern.factory;

import com.baeldung.hexagonpattern.domain.LibraryMaterialBorrowLogic;

public class MaterialsOrderFactory {

	public static LibraryMaterialBorrowLogic getMaterialOrderProcessor() {
		return new LibraryMaterialBorrowLogic();
	}

}
