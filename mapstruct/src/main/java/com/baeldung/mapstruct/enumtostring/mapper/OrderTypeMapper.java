package com.baeldung.mapstruct.enumtostring.mapper;

import com.baeldung.mapstruct.enumtostring.model.OrderType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The order type mapper.
 */
@Mapper
public interface OrderTypeMapper {
    /**
     * The order mapper.
     */
    OrderTypeMapper INSTANCE = Mappers.getMapper(OrderTypeMapper.class);

    /**
     * Maps order type to string value.
     *
     * @param source the source order type
     * @return the string value for the enum
     */
    String toString(OrderType source);

    /**
     * Maps a string order type to an enum order type.
     *
     * @param source the source
     * @return the order type enum
     */
    OrderType toOrderType(String source);
}
