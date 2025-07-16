package com.baeldung.spring;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dto.MediaDto;
import com.baeldung.entity.Media;
import com.baeldung.service.MediaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class MediaServiceSpringMockedMapperUnitTest {

    @Autowired
    MediaService mediaService;

    @MockitoBean
    MediaSpringMapper mockMediaMapper;

    @Test
    public void whenMockedSpringMapperIsUsed_thenMockedValuesAreMapped() {
        Media mockedMedia = new Media(12L, "title 12");
        when(mockMediaMapper.toEntity(ArgumentMatchers.any())).thenReturn(mockedMedia);

        MediaDto mediaDto = new MediaDto(1L, "title 1");
        Media persisted = mediaService.persistMedia(mediaDto);

        verify(mockMediaMapper).toEntity(mediaDto);
        assertEquals(mockedMedia.getId(), persisted.getId());
        assertEquals(mockedMedia.getTitle(), persisted.getTitle());
    }

}
