package com.baeldung.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.dto.MediaDto;
import com.baeldung.entity.Media;
import com.baeldung.mapper.MediaMapper;

public class MediaServiceGeneratedMapperUnitTest {

    @Test
    public void whenGeneratedMapperIsUsed_thenActualValuesAreMapped() {
        MediaService mediaService = new MediaService(Mappers.getMapper(MediaMapper.class));
        MediaDto mediaDto = new MediaDto(1L, "title 1");
        Media persisted = mediaService.persistMedia(mediaDto);
        assertEquals(mediaDto.getId(), persisted.getId());
        assertEquals(mediaDto.getTitle(), persisted.getTitle());
    }

}
