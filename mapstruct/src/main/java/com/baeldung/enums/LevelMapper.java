package com.baeldung.enums;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper
interface LevelMapper {

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.THROW_EXCEPTION)
    OutputLevel inputLevelToOutputLevel(InputLevel inputLevel);

}
