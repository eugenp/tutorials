package de.sample.quarkus.todos.boundary;

import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.samples.quarkus.todos.boundary.TodoDto;

class TodoDtoTest {

	@Test
	void testSetId() {
		var dto = new TodoDto();
		dto.setId(5L);
		var id = dto.getId();
		Assertions.assertEquals(5L, id);
	}
	
}
