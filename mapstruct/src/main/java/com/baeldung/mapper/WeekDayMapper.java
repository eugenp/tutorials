package com.baeldung.mapper;

import com.baeldung.dto.WeekDayNumber;
import com.baeldung.enums.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.DayOfWeek;

/**
 * The Week day mapper.
 */
@Mapper
public interface WeekDayMapper {

    /**
     * The constant INSTANCE.
     */
    WeekDayMapper INSTANCE = Mappers.getMapper(WeekDayMapper.class);

    /**
     * Convert week day to integer integer.
     *
     * @param source the source
     * @return the integer
     */
    default Integer convertWeekDayToInteger(WeekDay source) {
        Integer result = null;
        switch (source) {
            case Mon:
                result = 1;
                break;
            case Tue:
                result = 2;
                break;
            case Wed:
                result = 3;
                break;
            case Thu:
                result = 4;
                break;
            case Fri:
                result = 5;
                break;
            case Sat:
                result = 6;
                break;
            case Sun:
                result = 7;
                break;
        }
        return result;
    }

    /**
     * Maps day of week to week day.
     *
     * @param source the source
     * @return the week day
     */
    @ValueMapping(target = "Mon", source = "MONDAY")
    @ValueMapping(target = "Tue", source = "TUESDAY")
    @ValueMapping(target = "Wed", source = "WEDNESDAY")
    @ValueMapping(target = "Thu", source = "THURSDAY")
    @ValueMapping(target = "Fri", source = "FRIDAY")
    @ValueMapping(target = "Sat", source = "SATURDAY")
    @ValueMapping(target = "Sun", source = "SUNDAY")
    WeekDay toWeekDay(DayOfWeek source);

    /**
     * Maps a string to week day.
     *
     * @param source the source
     * @return the week day
     */
    @ValueMapping(target = "Mon", source = "MONDAY")
    @ValueMapping(target = "Tue", source = "TUESDAY")
    @ValueMapping(target = "Wed", source = "WEDNESDAY")
    @ValueMapping(target = "Thu", source = "THURSDAY")
    @ValueMapping(target = "Fri", source = "FRIDAY")
    @ValueMapping(target = "Sat", source = "SATURDAY")
    @ValueMapping(target = "Sun", source = "SUNDAY")
    WeekDay stringToWeekDay(String source);


    /**
     * Maps a week day to string.
     *
     * @param source the source
     * @return the week day
     */
    @ValueMapping(target = "MONDAY", source = "Mon")
    @ValueMapping(target = "TUESDAY", source = "Tue")
    @ValueMapping(target = "WEDNESDAY", source = "Wed")
    @ValueMapping(target = "THURSDAY", source = "Thu")
    @ValueMapping(target = "FRIDAY", source = "Fri")
    @ValueMapping(target = "SATURDAY", source = "Sat")
    @ValueMapping(target = "SUNDAY", source = "Sun")
    String weekDayToString(WeekDay source);

    /**
     * Maps a week day to number.
     *
     * @param source the source
     * @return the week day number
     */
    @Mapping(target = "number", source = ".")
    WeekDayNumber weekDayToWeekDayNumber(WeekDay source);

    /**
     * Maps day of week to work day.
     *
     * @param source the source
     * @return the work day
     */
    @ValueMapping(target = "Mon", source = "Mon")
    @ValueMapping(target = "Tue", source = "Tue")
    @ValueMapping(target = "Wed", source = "Wed")
    @ValueMapping(target = "Thu", source = "Thu")
    @ValueMapping(target = "Fri", source = "Fri")
    @ValueMapping(target = "Mon", source = "Sat")
    @ValueMapping(target = "Mon", source = "Sun")
    WorkWeekDay weekDayToWorkWeekDay(WeekDay source);

    /**
     * Maps day of week to work week day.
     *
     * @param source the source
     * @return the work day
     */
    @ValueMapping(target = "Mon", source = "Mon")
    @ValueMapping(target = "Tue", source = "Tue")
    @ValueMapping(target = "Wed", source = "Wed")
    @ValueMapping(target = "Thu", source = "Thu")
    @ValueMapping(target = "Fri", source = "Fri")
    @ValueMapping(target = "Mon", source = MappingConstants.ANY_REMAINING)
    WorkWeekDay weekDayToWorkWeekDayWithRemaining(WeekDay source);

    /**
     * Maps day of week to work day.
     *
     * @param source the source
     * @return the work day
     */
    @ValueMapping(target = "Mon", source = "Mon")
    @ValueMapping(target = "Tue", source = "Tue")
    @ValueMapping(target = "Wed", source = "Wed")
    @ValueMapping(target = "Thu", source = "Thu")
    @ValueMapping(target = "Fri", source = "Fri")
    @ValueMapping(target = "Mon", source = MappingConstants.ANY_UNMAPPED)
    WorkWeekDay weekDayToWorkWeekDayWithUnmapped(WeekDay source);

    /**
     * Maps day of week to work day with null handling.
     *
     * @param source the source
     * @return the work day
     */
    @ValueMapping(target = "Mon", source = "Mon")
    @ValueMapping(target = MappingConstants.NULL, source = "Tue")
    @ValueMapping(target = "Wed", source = "Wed")
    @ValueMapping(target = MappingConstants.NULL, source = "Thu")
    @ValueMapping(target = "Fri", source = "Fri")
    @ValueMapping(target = "Wed", source = MappingConstants.NULL)
    @ValueMapping(target = MappingConstants.NULL, source = MappingConstants.ANY_UNMAPPED)
    WorkWeekDay weekDayToWorkWeekDayWithNullHandling(WeekDay source);

    /**
     * Maps day of week to work day with exception handling.
     *
     * @param source the source
     * @return the work day
     * @throws IllegalArgumentException if {@code source} is {@code Sat} or {@code Sun}.
     */
    @ValueMapping(target = "Mon", source = "Mon")
    @ValueMapping(target = "Tue", source = "Tue")
    @ValueMapping(target = "Wed", source = "Wed")
    @ValueMapping(target = "Thu", source = "Thu")
    @ValueMapping(target = "Fri", source = "Fri")
    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MappingConstants.ANY_UNMAPPED)
    WorkWeekDay weekDayToWorkWeekDayWithExceptionHandling(WeekDay source);

    /**
     * Apply suffix.
     *
     * @param source the source
     * @return the string
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.SUFFIX_TRANSFORMATION, configuration = "_Value")
    WeekDaySuffixed applySuffix(WeekDay source);

    /**
     * Apply prefix.
     *
     * @param source the source
     * @return the string
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.PREFIX_TRANSFORMATION, configuration = "Value_")
    WeekDayPrefixed applyPrefix(WeekDay source);

    /**
     * Strip suffix week day.
     *
     * @param source the source
     * @return the week day
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.STRIP_SUFFIX_TRANSFORMATION, configuration = "_Value")
    WeekDay stripSuffix(WeekDaySuffixed source);

    /**
     * Strip prefix week day.
     *
     * @param source the source
     * @return the week day
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.STRIP_PREFIX_TRANSFORMATION, configuration = "Value_")
    WeekDay stripPrefix(WeekDayPrefixed source);

    /**
     * Apply lowercase week day lowercase.
     *
     * @param source the source
     * @return the week day lowercase
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    WeekDayLowercase applyLowercase(WeekDay source);

    /**
     * Apply uppercase week day uppercase.
     *
     * @param source the source
     * @return the week day uppercase
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    WeekDayUppercase applyUppercase(WeekDay source);

    /**
     * Underscore tp capital string.
     *
     * @param source the source
     * @return the string
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "capital")
    String underscoreToCapital(WeekDayUnderscore source);

    /**
     * Lowercase to capital week day.
     *
     * @param source the source
     * @return the week day
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "capital")
    WeekDay lowercaseToCapital(WeekDayLowercase source);
}
