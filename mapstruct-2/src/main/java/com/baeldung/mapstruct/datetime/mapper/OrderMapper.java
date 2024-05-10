package com.baeldung.mapstruct.datetime.mapper;

import com.baeldung.mapstruct.datetime.model.LocalOrder;
import com.baeldung.mapstruct.datetime.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * The order mapper.
 */
@Mapper
public interface OrderMapper {
    /**
     * The mapper instance.
     */
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    /**
     * The constant DEFAULT_ZONE.
     */
    ZoneOffset DEFAULT_ZONE = ZoneOffset.UTC;

    /**
     * Converts local date time to an instant.
     *
     * @param localDateTime the local date time
     * @return the instant
     */
    @Named("localDateTimeToInstant")
    default Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime.toInstant(DEFAULT_ZONE);
    }

    /**
     * Instant to local date time local date time.
     *
     * @param instant the instant
     * @return the local date time
     */
    @Named("instantToLocalDateTime")
    default LocalDateTime instantToLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, DEFAULT_ZONE);
    }

    /**
     * Maps a local order to order.
     *
     * @param source the source
     * @return the instant
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "localDateTimeToInstant")
    Order toOrder(LocalOrder source);

    /**
     * Maps local order to order.
     *
     * @param source the source
     * @return the local order
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "instantToLocalDateTime")
    LocalOrder toLocalOrder(Order source);
}
