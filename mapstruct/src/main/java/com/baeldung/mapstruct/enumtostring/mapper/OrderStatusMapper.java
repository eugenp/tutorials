package com.baeldung.mapstruct.enumtostring.mapper;

import com.baeldung.mapstruct.enumtostring.model.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The order status mapper.
 */
@Mapper
public interface OrderStatusMapper {
    /**
     * The order mapper.
     */
    OrderStatusMapper INSTANCE = Mappers.getMapper(OrderStatusMapper.class);

    /**
     * Maps order status to a string value.
     *
     * @param source the source order status
     * @return the string value for the enum
     */
    String toString(OrderStatus source);

    /**
     * Maps a string order status to an enum order status.
     *
     * @param source the source
     * @return the order status enum
     */
    OrderStatus toOrderStatus(String source);
}
