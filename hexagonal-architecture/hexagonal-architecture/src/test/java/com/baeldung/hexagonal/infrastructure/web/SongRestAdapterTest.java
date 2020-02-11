package com.baeldung.hexagonal.infrastructure.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.hexagonal.application.Config;
import com.baeldung.hexagonal.application.domain.Song;
import com.baeldung.hexagonal.infrastructure.persistence.inmemory.InMemoryAdapterConfig;
import com.baeldung.hexagonal.infrastructure.persistence.jpa.SpringDataJpaAdapterConfig;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SongRestAdapter.class)
@ContextConfiguration(classes= {Config.class, InMemoryAdapterConfig.class, SpringDataJpaAdapterConfig.class})
class SongRestAdapterTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
	protected SongRestAdapter songRestAdapter;
	
	@Before
	public void setUp() {
		Mockito.reset(songRestAdapter);
	}
	
	@SuppressWarnings("unused")
	@Test
	void shouldCreateSong() throws Exception {
		
		Integer id = 1;
		String name = "God Bless the USA";
		String description = "American patriotic song";
		
		// given
				String songJson = "{\r\n" + 
						"    \"songId\": "+id+",\r\n" + 
						"    \"name\": \""+name+"\",\r\n" + 
						"    \"description\": \""+description+"\"\r\n" + 
						"}";
				
				Song song = new Song(id, name, description);
				
		// when
		when(songRestAdapter.addSong(song)).thenReturn(new Integer(id));
		
		// then
		mockMvc.perform(post("/song")
				.content(songJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
		
	}

}
