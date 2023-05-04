package com.baeldung.spring.data.jpa.naturalid;

import com.baeldung.spring.data.jpa.naturalid.entity.ConferenceRoom;
import com.baeldung.spring.data.jpa.naturalid.entity.GuestRoom;
import com.baeldung.spring.data.jpa.naturalid.repository.ConferenceRoomJpaRepo;
import com.baeldung.spring.data.jpa.naturalid.repository.ConferenceRoomRepository;
import com.baeldung.spring.data.jpa.naturalid.repository.GuestRoomJpaRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NaturalIdIntegrationTest {

	@Autowired
	private HotelRoomsService service;

	@Autowired
	private GuestRoomJpaRepo guestRoomJpaRepo;
	@Autowired
	private ConferenceRoomJpaRepo conferenceRoomJpaRepo;

	@Autowired
	private ConferenceRoomRepository conferenceRoomRepository;

	@Test
	void whenWeFindByNaturalKey_thenEntityIsReturnedCorrectly() {
		guestRoomJpaRepo.save(new GuestRoom(23, 3, "B-423", 4));

		Optional<GuestRoom> result = service.guestRoom(23, 3);

		assertThat(result).isPresent()
		  .hasValueSatisfying(room -> "B-423".equals(room.getName()));
	}

	@Test
	void whenWeFindBySimpleNaturalKey_thenEntityIsReturnedCorrectly() {
		conferenceRoomJpaRepo.save(new ConferenceRoom("Colorado", 100));

		Optional<ConferenceRoom> result = service.conferenceRoom("Colorado");

		assertThat(result).isPresent()
		  .hasValueSatisfying(room -> "Colorado".equals(room.getName()));
	}

	@Test
	void givenNaturalIdRepo_whenWeFindBySimpleNaturalKey_thenEntityIsReturnedCorrectly() {
		conferenceRoomJpaRepo.save(new ConferenceRoom("Nevada", 200));

		Optional<ConferenceRoom> result = conferenceRoomRepository.naturalId("Nevada");

		assertThat(result).isPresent()
		  .hasValueSatisfying(room -> "Nevada".equals(room.getName()));
	}
}