package com.baeldung.context.mapper;

import org.mapstruct.BeforeMapping;
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
public abstract class TradeMapperWithBeforeMapping {
    final Logger logger = LoggerFactory.getLogger(TradeMapperWithBeforeMapping.class);

    protected SecurityService securityService;

    public static TradeMapperWithBeforeMapping getInstance() {
        return Mappers.getMapper(TradeMapperWithBeforeMapping.class);
    }

    @BeforeMapping
    protected void initialize(@Context Integer exchangeCode) {
        logger.info("initialize(): Initializing SecurityService with identifier type: {}", exchangeCode);
        securityService = new SecurityService(exchangeCode);
    }

    @Mapping(target="securityIdentifier", expression = "java(securityService.getSecurityIdentifierOfType(trade.getSecurityID(), identifierType))")
    protected abstract TradeDto toTradeDto(Trade trade, @Context String identifierType, @Context Integer exchangeCode);

}
