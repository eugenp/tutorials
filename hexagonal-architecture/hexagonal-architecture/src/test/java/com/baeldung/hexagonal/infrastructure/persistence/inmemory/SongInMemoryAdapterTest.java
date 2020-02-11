package com.baeldung.hexagonal.infrastructure.persistence.inmemory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.hexagonal.application.domain.Song;

@ActiveProfiles("in-memory")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InMemoryAdapterConfig.class)
class SongInMemoryAdapterTest {

	@Autowired
	private SongInMemoryAdapter songInMemoryAdapter;

	@Test
	void testAddSongUseCase() {

		Integer expectedId = 1;
		String expectedName = "God Bless the USA";
		String expectedDescription = "American patriotic song";
		
		Song song = new Song();
		song.setSongtId(expectedId);
		song.setName(expectedName);
		song.setDescription(expectedDescription);
		
		songInMemoryAdapter.removeSongs();
		
		assertThat(songInMemoryAdapter.getSongs()).isNotNull().isEmpty();
		
		songInMemoryAdapter.addSong(song);
		
		assertThat(songInMemoryAdapter.getSongs()).isNotNull().isNotEmpty();
		
		assertThat(songInMemoryAdapter.getSongById(expectedId).getName()).isEqualTo(expectedName);
		assertThat(songInMemoryAdapter.getSongById(expectedId).getDescription()).isEqualTo(expectedDescription);

		
		
	}

}
