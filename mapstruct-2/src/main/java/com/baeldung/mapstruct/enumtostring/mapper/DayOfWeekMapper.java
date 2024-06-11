package com.baeldung.mapstruct.enumtostring.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

import java.time.DayOfWeek;

@Mapper
public interface DayOfWeekMapper {
    DayOfWeekMapper INSTANCE = Mappers.getMapper(DayOfWeekMapper.class);

    String toString(DayOfWeek dayOfWeek);

    @ValueMapping(target = "MONDAY", source = MappingConstants.NULL)
    String toStringWithDefault(DayOfWeek dayOfWeek);

    DayOfWeek nameStringToDayOfWeek(String day);

    @ValueMapping(target = "MONDAY", source = MappingConstants.ANY_UNMAPPED)
    DayOfWeek nameStringToDayOfWeekWithDefaults(String day);

    @ValueMapping(target = "Mon", source = "MONDAY")
    @ValueMapping(target = "Tue", source = "TUESDAY")
    @ValueMapping(target = "Wed", source = "WEDNESDAY")
    @ValueMapping(target = "Thu", source = "THURSDAY")
    @ValueMapping(target = "Fri", source = "FRIDAY")
    @ValueMapping(target = "Sat", source = "SATURDAY")
    @ValueMapping(target = "Sun", source = "SUNDAY")
    String toShortString(DayOfWeek dayOfWeek);

    @InheritInverseConfiguration(name = "toShortString")
    DayOfWeek shortStringToDayOfWeek(String day);
}
