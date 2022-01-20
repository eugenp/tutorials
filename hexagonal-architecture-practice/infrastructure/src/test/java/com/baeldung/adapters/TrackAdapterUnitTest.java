package com.baeldung.adapters;

import com.baeldung.data.Song;
import com.baeldung.entity.Track;
import com.baeldung.repository.TrackRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class TrackAdapterUnitTest {
    @Spy
    @InjectMocks
    private TrackAdapter trackAdapter;

    @Mock
    private TrackRepository repository;

    @Mock
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUnitTest() {
        Song song = new Song("title", "unknown", 300L);
        trackAdapter.save(song);
        verify(repository, times(1)).save(any(Track.class));
    }
}