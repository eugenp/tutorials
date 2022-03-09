package org.baeldung.eval.hexagonalarchitecture.port.input.web;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.baeldung.eval.hexagonalarchitecture.adapter.web.PoemControllerImpl;
import org.baeldung.eval.hexagonalarchitecture.core.PoemService;
import org.baeldung.eval.hexagonalarchitecture.data.Poem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PoemControllerImpl.class)
@ContextConfiguration(classes = { PoemController.class, PoemControllerImpl.class })
public class PoemControllerTest {

	private static final String TEST_AUTHOR = "Robert Frost";
	private static final String TEST_POEM_TITLE = "Stopping by Woods on a Snowy Evening";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PoemService poemServiceMock;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void testAddPoems_whenAddPoem_thenEntityIsPortedToService() throws Exception {
		Poem testPoem = Poem.builder().author(TEST_AUTHOR).title(TEST_POEM_TITLE).build();
		mvc.perform(MockMvcRequestBuilders
			.post("/poems").content(objectMapper.writeValueAsString(testPoem))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
		verify(poemServiceMock, only()).addPoems(testPoem);
	}

	@Test
	void testGetPoemsById_success() throws Exception {
		final Poem testPoem = Poem.builder().author(TEST_AUTHOR).title(TEST_POEM_TITLE).build();
		UUID testId = UUID.randomUUID();
		when(poemServiceMock.getPoemById(testId)).thenReturn(testPoem);
		mvc.perform(MockMvcRequestBuilders
			.get("/poems/" + testId)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		verify(poemServiceMock, only()).getPoemById(testId);
	}

	@Test
	void testGetPoemsById_failure() throws Exception {
		UUID testId = UUID.randomUUID();
		mvc.perform(MockMvcRequestBuilders
			.get("/poems/" + testId)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.author").doesNotExist());
		verify(poemServiceMock, only()).getPoemById(testId);
	}

	
	@Test 
	void testPoems() throws Exception {
		final Poem testPoem = Poem.builder().author(TEST_AUTHOR).title(TEST_POEM_TITLE).build();
		List<Poem> poems =new ArrayList<>();
		poems.add(testPoem);
		when(poemServiceMock.getAllPoems()).thenReturn(poems);
		mvc.perform(MockMvcRequestBuilders
			.get("/poems")
			.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].author").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(TEST_AUTHOR));
		verify(poemServiceMock,only()).getAllPoems();
	}
	
	@Test 
	void testRemovePoems() throws Exception {
		final Poem testPoem = Poem.builder().author(TEST_AUTHOR).title(TEST_POEM_TITLE).build();
		mvc.perform(MockMvcRequestBuilders
			.delete("/poems")
			.content(objectMapper.writeValueAsString(testPoem))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk());
		verify(poemServiceMock,only()).removePoems(testPoem);
	}
	
	@Test 
	void testUpdatePoems( )throws Exception {
		final Poem testPoem = Poem.builder().author(TEST_AUTHOR).title(TEST_POEM_TITLE).build();
		mvc.perform(MockMvcRequestBuilders
			.put("/poems")
			.content(objectMapper.writeValueAsString(testPoem))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk());
		verify(poemServiceMock,only()).updatePoems(testPoem);
	}
	
}
