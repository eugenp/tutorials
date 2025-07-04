package com.baeldung.spring;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dto.MediaDto;
import com.baeldung.entity.Media;
import com.baeldung.service.MediaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class, MediaSpringMapperImpl.class })
public class MediaServiceSpringGeneratedMapperUnitTest {

    @Autowired
    MediaService mediaService;

    @Test
    public void whenGeneratedSpringMapperIsUsed_thenActualValuesAreMapped() {
        MediaDto mediaDto = new MediaDto(1L, "title 1");
        Media persisted = mediaService.persistMedia(mediaDto);
        assertEquals(mediaDto.getId(), persisted.getId());
        assertEquals(mediaDto.getTitle(), persisted.getTitle());
    }

}
