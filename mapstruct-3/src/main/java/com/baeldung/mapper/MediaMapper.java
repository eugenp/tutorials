package com.baeldung.mapper;

import org.mapstruct.Mapper;

import com.baeldung.dto.MediaDto;
import com.baeldung.entity.Media;

@Mapper
public interface MediaMapper {

    MediaDto toDto(Media media);

    Media toEntity(MediaDto mediaDto);

}
