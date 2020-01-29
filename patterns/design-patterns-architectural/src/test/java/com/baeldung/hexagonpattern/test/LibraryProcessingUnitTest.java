package com.baeldung.hexagonpattern.test;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.baeldung.hexagonpattern.domain.LibraryMaterialStatus;
import com.baeldung.hexagonpattern.domain.LibraryMaterialType;
import com.baeldung.hexagonpattern.factory.AdapterFactory;
import com.baeldung.hexagonpattern.ports.ExtendMaterialPort;

public class LibraryProcessingUnitTest {

	@Test
	public void givenStatusOfBooks_whenProcessed_thenTwoAssertion() throws Exception {
		ExtendMaterialPort itemProcessor = AdapterFactory.getOrderProcessor("MOBILE");
		ArrayList<LibraryMaterialType> items = new ArrayList<LibraryMaterialType>();
		items.add(itemProcessor.createItem("BOOKS"));
		LibraryMaterialStatus status = itemProcessor.processMaterialOrder(items);
		assertThat(status.getMaterialStatus()).contains("extended").doesNotContain("failed");
		assertThat(status.getDueDate()).isInstanceOf(LocalDate.class);
	}

	@Test
	public void givenStatusOfInvalidItem_whenProcessed_thenOneAssertion() throws Exception {
		ExtendMaterialPort itemProcessor = AdapterFactory.getOrderProcessor("MOBILE");
		ArrayList<LibraryMaterialType> items = new ArrayList<LibraryMaterialType>();
		items.add(itemProcessor.createItem("CDS"));
		LibraryMaterialStatus status = itemProcessor.processMaterialOrder(items);
		assertThat(status.getMaterialStatus()).contains("failed").doesNotContain("extended");
	}

	@Test
	public void givenInvalidEntryPoint_whenProcessed_thenOneAssertion() throws Exception {
		assertThatThrownBy(() -> {
			ExtendMaterialPort itemProcessor = AdapterFactory.getOrderProcessor("Counter");
		}).isInstanceOf(Exception.class)
				.hasMessageContaining("Since it is a holiday, extending the book from  COUNTER is not possible");
	}
}
