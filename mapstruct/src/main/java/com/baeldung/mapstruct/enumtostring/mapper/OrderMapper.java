package com.baeldung.mapstruct.enumtostring.mapper;

import com.baeldung.mapstruct.enumtostring.model.ExternalOrder;
import com.baeldung.mapstruct.enumtostring.model.Order;
import com.baeldung.mapstruct.enumtostring.model.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The order mapper.
 */
@Mapper(uses = {OrderStatusMapper.class, OrderTypeMapper.class})
public interface OrderMapper {
    /**
     * The order mapper.
     */
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * Maps order to external order.
     *
     * @param source the source order
     * @return the external order
     */
    ExternalOrder toExternalOrder(Order source);

    /**
     * Maps string order status to enum order status.
     *
     * @param source the source
     * @return the order status enum
     */
    OrderStatus toOrderStatus(String source);
}
