package com.baeldung.mapper;

import org.mapstruct.Mapper;

import com.baeldung.dto.SimpleSource;
import com.baeldung.entity.LombokDestination;
import com.baeldung.entity.SimpleDestination;

@Mapper
public interface LombokMapper {

    SimpleDestination sourceToDestination(SimpleSource source);

    LombokDestination sourceToLombokDestination(SimpleSource source);

}
