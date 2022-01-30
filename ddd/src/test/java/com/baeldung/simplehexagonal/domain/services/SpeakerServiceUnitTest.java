package com.baeldung.simplehexagonal.domain.services;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.notNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.simplehexagonal.domain.Speaker;
import com.baeldung.simplehexagonal.domain.repository.SpeakerRepository;
import com.baeldung.simplehexagonal.domain.services.SpeakerServiceImpl;

public class SpeakerServiceUnitTest {

    private SpeakerServiceImpl speakerService;

    private SpeakerRepository speakerRepository;

    Speaker speaker;

    @BeforeEach
    void setUp() {
        speakerRepository = mock(SpeakerRepository.class);
        speakerService = new SpeakerServiceImpl(speakerRepository);

        speaker = new Speaker();
        speaker.setSpeakerId(1L);
        speaker.setTitle("Mr");
        speaker.setFirstName("Palani");
        speaker.setLastName("Arun");

        when(speakerRepository.save(Mockito.any())).thenReturn(speaker);
        when(speakerRepository.findById(1L)).thenReturn(speaker);
    }

    @Test
    void testFindAll() {
        List<Speaker> list = speakerService.findAll();
        notNull(list, "should not return null");
    }

    @Test
    void testGet() {
        Speaker mySpeaker = speakerService.get(1L);
        notNull(mySpeaker, "should not return null");
    }

    @Test
    void testCreate() {
        speaker = speakerService.save(new Speaker());
        notNull(speaker.getSpeakerId(), "Id should be populated");
    }

    @Test
    void testDelete() {
        try {
            speakerService.delete(1L);
        } catch (Exception e) {
            fail("Should not throw error");
        }
    }

    @Test
    void testUpdate() {
        Speaker updatedSpeaker = speakerService.update(1L, new Speaker());
        notNull(updatedSpeaker, "Id should be populated");
    }

}
