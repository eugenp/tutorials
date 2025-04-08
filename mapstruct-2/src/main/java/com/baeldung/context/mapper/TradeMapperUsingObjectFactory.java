package com.baeldung.context.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.context.entity.Trade;
import com.baeldung.context.entity.TradeDto;
import com.baeldung.context.service.TradeFactory;

@Mapper(uses = TradeFactory.class)
public abstract class TradeMapperUsingObjectFactory {
    final Logger logger = LoggerFactory.getLogger(TradeMapperUsingObjectFactory.class);

    public static TradeMapperUsingObjectFactory getInstance() {
        return Mappers.getMapper(TradeMapperUsingObjectFactory.class);
    }

    protected abstract TradeDto toTradeDto(Trade trade, @Context String identifierType);
}
