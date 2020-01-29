package com.baeldung.hexagonpattern.ports;

import java.util.List;

import com.baeldung.hexagonpattern.domain.LibraryMaterialStatus;
import com.baeldung.hexagonpattern.domain.LibraryMaterialType;

public interface ExtendMaterialPort {

	LibraryMaterialType createItem(String item);

	LibraryMaterialStatus processMaterialOrder(List<LibraryMaterialType> items) throws Exception;

}
