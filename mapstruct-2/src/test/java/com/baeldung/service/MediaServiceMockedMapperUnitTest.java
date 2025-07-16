package com.baeldung.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.baeldung.dto.MediaDto;
import com.baeldung.entity.Media;
import com.baeldung.mapper.MediaMapper;

public class MediaServiceMockedMapperUnitTest {

    @Test
    public void whenMockedMapperIsUsed_thenMockedValuesAreMapped() {
        MediaMapper mockMediaMapper = mock(MediaMapper.class);
        Media mockedMedia = new Media(5L, "Title 5");
        when(mockMediaMapper.toEntity(any())).thenReturn(mockedMedia);

        MediaService mediaService = new MediaService(mockMediaMapper);
        MediaDto mediaDto = new MediaDto(1L, "title 1");
        Media persisted = mediaService.persistMedia(mediaDto);

        verify(mockMediaMapper).toEntity(mediaDto);
        assertEquals(mockedMedia.getId(), persisted.getId());
        assertEquals(mockedMedia.getTitle(), persisted.getTitle());
    }

}
