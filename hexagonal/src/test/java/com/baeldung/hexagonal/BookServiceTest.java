package com.baeldung.hexagonal;

import org.junit.Assert;
import org.junit.Test;

public class BookServiceTest {

	@Test
	public void whenASearchingIsbn_thenFind() {
		BookService service = new BookService(new BookDaoMock());
		Book actual = service.search("mock");

		Assert.assertEquals("mock", actual.getTitle());
		Assert.assertEquals("mock", actual.getIsbn());
		Assert.assertEquals("mock", actual.getTitle());

		Assert.assertNull(service.search("wrong-isbn"));
	}
}
