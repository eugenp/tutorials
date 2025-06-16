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
        MediaService testee = new MediaService(Mappers.getMapper(MediaMapper.class));
        MediaDto mediaDto = new MediaDto(1L, "title 1");
        Media persisted = testee.persistMedia(mediaDto);
        assertEquals(persisted.getId(), mediaDto.getId());
        assertEquals(persisted.getTitle(), mediaDto.getTitle());
    }

}
