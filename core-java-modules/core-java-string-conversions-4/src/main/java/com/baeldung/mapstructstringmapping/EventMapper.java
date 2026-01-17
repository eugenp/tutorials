package com.baeldung.mapstructstringmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "eventDate", target = "eventDate", dateFormat = "yyyy-MM-dd")
    EventDTO toEventDTO(Event event);
}
