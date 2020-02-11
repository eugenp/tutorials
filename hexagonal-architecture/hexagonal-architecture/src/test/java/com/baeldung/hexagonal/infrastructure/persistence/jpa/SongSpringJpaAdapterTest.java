package com.baeldung.hexagonal.infrastructure.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.baeldung.hexagonal.application.domain.Song;

@ActiveProfiles("jpa")
@DataJpaTest
@ContextConfiguration(classes = SpringDataJpaAdapterConfig.class)
@EntityScan(basePackages="com.baeldung.hexagonal.infrastructure.persistence.jpa")
class SongSpringJpaAdapterTest {

	@Autowired
	private SongSpringJpaAdapter songSpringJpaAdapter;

	@Test
	void testAddSongUseCase() {

		//Integer expectedId = 1;
		String expectedName = "God Bless the USA";
		String expectedDescription = "American patriotic song";
		
		Song song = new Song();
		//song.setSongtId(expectedId);
		song.setName(expectedName);
		song.setDescription(expectedDescription);
		
		songSpringJpaAdapter.removeSongs();
		
		assertThat(songSpringJpaAdapter.getSongs()).isNotNull().isEmpty();
		
		Integer realId = songSpringJpaAdapter.addSong(song);
		
		assertThat(songSpringJpaAdapter.getSongs()).isNotNull().isNotEmpty();
		
		assertThat(songSpringJpaAdapter.getSongById(realId).getName()).isEqualTo(expectedName);
		assertThat(songSpringJpaAdapter.getSongById(realId).getDescription()).isEqualTo(expectedDescription);

		
		
	}

}
