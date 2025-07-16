package com.baeldung.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.dto.MediaDto;
import com.baeldung.entity.Media;
import com.baeldung.mapper.MediaMapper;

public class MediaService {

    final Logger logger = LoggerFactory.getLogger(MediaService.class);

    private final MediaMapper mediaMapper;

    public MediaService(MediaMapper mediaMapper) {
        this.mediaMapper = mediaMapper;
    }

    public Media persistMedia(MediaDto mediaDto) {
        Media media = mediaMapper.toEntity(mediaDto);
        logger.info("Persist media: {}", media);
        return media;
    }

}
