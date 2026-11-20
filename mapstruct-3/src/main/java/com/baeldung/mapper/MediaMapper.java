package com.baeldung.mapper;

import com.baeldung.dto.MediaDto;
import com.baeldung.entity.Media;
import org.mapstruct.Mapper;

@Mapper
public interface MediaMapper {

    MediaDto toDto(Media media);

    Media toEntity(MediaDto mediaDto);

}
