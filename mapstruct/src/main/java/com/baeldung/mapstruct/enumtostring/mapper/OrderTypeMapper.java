package com.baeldung.mapstruct.enumtostring.mapper;

import com.baeldung.mapstruct.enumtostring.model.OrderType;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;
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
    @Named("orderTypeToString")
    String toString(OrderType source);

    /**
     * Maps an order type to string display value.
     *
     * @param source the source order type
     * @return the display string value for the enum
     */
    @ValueMapping(target = "Big Savings", source = "BULK")
    @ValueMapping(target = "Daily Needs", source = "REGULAR")
    @ValueMapping(target = "Season Sale", source = "SALE")
    @ValueMapping(target = "Subscribe to Save", source = "SUBSCRIPTION")
    String toDisplayString(OrderType source);

    /**
     * Maps a string order type to an enum order type.
     *
     * @param source the source
     * @return the order type enum
     */
    OrderType toOrderType(String source);
}
