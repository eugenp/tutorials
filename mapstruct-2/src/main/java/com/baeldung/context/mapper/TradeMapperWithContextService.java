package com.baeldung.context.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.context.entity.Trade;
import com.baeldung.context.entity.TradeDto;
import com.baeldung.context.service.SecurityService;

@Mapper
public abstract class TradeMapperWithContextService {
    final Logger logger = LoggerFactory.getLogger(TradeMapperWithContextService.class);

    public static TradeMapperWithContextService getInstance() {
        return Mappers.getMapper(TradeMapperWithContextService.class);
    }

    @Mapping(target="securityIdentifier", expression = "java(securityService.getSecurityIsin(trade.getSecurityID()))")
    protected abstract TradeDto toTradeDto(Trade trade, @Context SecurityService securityService);
}