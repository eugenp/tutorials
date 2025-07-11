package com.baeldung.spring;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.baeldung.mapper.MediaMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaSpringMapper extends MediaMapper {
}
