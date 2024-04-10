package com.baeldung.mapstruct.enums.mapper;

import com.baeldung.mapstruct.enums.model.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * The Traffic signal mapper.
 */
@Mapper
public interface TrafficSignalMapper {

    /**
     * The constant INSTANCE.
     */
    TrafficSignalMapper INSTANCE = Mappers.getMapper(TrafficSignalMapper.class);

    /**
     * Convert traffic signal to integer.
     *
     * @param source the source
     * @return the integer
     */
    default Integer convertTrafficSignalToInteger(TrafficSignal source) {
        Integer result = null;
        switch (source) {
            case Off:
                result = 0;
                break;
            case Stop:
                result = 1;
                break;
            case Go:
                result = 2;
                break;
        }
        return result;
    }

    /**
     * Maps road sign to traffic signal.
     *
     * @param source the source
     * @return the traffic signal
     */
    @ValueMapping(target = "Off", source = "Off")
    @ValueMapping(target = "Go", source = "Move")
    @ValueMapping(target = "Stop", source = "Halt")
    TrafficSignal toTrafficSignal(RoadSign source);

    /**
     * Maps string to traffic signal.
     *
     * @param source the source
     * @return the traffic signal
     */
    @ValueMapping(target = "Off", source = "Off")
    @ValueMapping(target = "Go", source = "Move")
    @ValueMapping(target = "Stop", source = "Halt")
    TrafficSignal stringToTrafficSignal(String source);


    /**
     * Maps a traffic signal to string.
     *
     * @param source the source
     * @return the traffic signal
     */
    @ValueMapping(target = "Off", source = "Off")
    @ValueMapping(target = "Go", source = "Go")
    @ValueMapping(target = "Stop", source = "Stop")
    String trafficSignalToString(TrafficSignal source);

    /**
     * Maps a traffic signal to number.
     *
     * @param source the source
     * @return the traffic signal number
     */
    @Mapping(target = "number", source = ".")
    TrafficSignalNumber trafficSignalToTrafficSignalNumber(TrafficSignal source);

    /**
     * Maps traffic signal to simple traffic signal.
     *
     * @param source the source
     * @return the simple traffic signal
     */
    @ValueMapping(target = "Off", source = "Off")
    @ValueMapping(target = "On", source = "Go")
    @ValueMapping(target = "Off", source = "Stop")
    SimpleTrafficSignal toSimpleTrafficSignal(TrafficSignal source);

    /**
     * Maps traffic signal to simple traffic signal.
     *
     * @param source the source
     * @return the simple traffic signal
     */
    @ValueMapping(target = "On", source = "Go")
    @ValueMapping(target = "Off", source = MappingConstants.ANY_REMAINING)
    SimpleTrafficSignal toSimpleTrafficSignalWithRemaining(TrafficSignal source);

    /**
     * Maps traffic signal to simple traffic signal.
     *
     * @param source the source
     * @return the simple traffic signal
     */
    @ValueMapping(target = "On", source = "Go")
    @ValueMapping(target = "Off", source = MappingConstants.ANY_UNMAPPED)
    SimpleTrafficSignal toSimpleTrafficSignalWithUnmapped(TrafficSignal source);

    /**
     * Maps traffic signal with null handling.
     *
     * @param source the source
     * @return the work day
     */
    @ValueMapping(target = "Off", source = MappingConstants.NULL)
    @ValueMapping(target = "On", source = "Go")
    @ValueMapping(target = MappingConstants.NULL, source = MappingConstants.ANY_UNMAPPED)
    SimpleTrafficSignal toSimpleTrafficSignalWithNullHandling(TrafficSignal source);

    /**
     * Maps simple traffic signal to traffic signal with exception handling.
     *
     * @param source the source
     * @return the work day
     * @throws IllegalArgumentException if {@code source} is {@code Sat} or {@code Sun}.
     */
    @ValueMapping(target = "On", source = "Go")
    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MappingConstants.NULL)
    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MappingConstants.ANY_UNMAPPED)
    SimpleTrafficSignal toSimpleTrafficSignalWithExceptionHandling(TrafficSignal source);

    /**
     * Apply suffix.
     *
     * @param source the source
     * @return traffic signal suffixed
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.SUFFIX_TRANSFORMATION, configuration = "_Value")
    TrafficSignalSuffixed applySuffix(TrafficSignal source);

    /**
     * Apply prefix.
     *
     * @param source the source
     * @return the traffic signal prefixed
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.PREFIX_TRANSFORMATION, configuration = "Value_")
    TrafficSignalPrefixed applyPrefix(TrafficSignal source);

    /**
     * Strip suffix.
     *
     * @param source the source
     * @return the traffic signal
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.STRIP_SUFFIX_TRANSFORMATION, configuration = "_Value")
    TrafficSignal stripSuffix(TrafficSignalSuffixed source);

    /**
     * Strip prefix.
     *
     * @param source the source
     * @return the traffic signal
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.STRIP_PREFIX_TRANSFORMATION, configuration = "Value_")
    TrafficSignal stripPrefix(TrafficSignalPrefixed source);

    /**
     * Apply lowercase to traffic signal.
     *
     * @param source the source
     * @return the traffic signal lowercase
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "lower")
    TrafficSignalLowercase applyLowercase(TrafficSignal source);

    /**
     * Apply uppercase to traffic signal.
     *
     * @param source the source
     * @return the traffic signal uppercase
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "upper")
    TrafficSignalUppercase applyUppercase(TrafficSignal source);

    /**
     * Underscore to string in capital case.
     *
     * @param source the source
     * @return the string
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "capital")
    String underscoreToCapital(TrafficSignalUnderscore source);

    /**
     * Lowercase to capital traffic signal.
     *
     * @param source the source
     * @return traffic signal
     */
    @EnumMapping(nameTransformationStrategy = MappingConstants.CASE_TRANSFORMATION, configuration = "capital")
    TrafficSignal lowercaseToCapital(TrafficSignalLowercase source);
}
