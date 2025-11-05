package com.baeldung.context.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.context.entity.Trade;
import com.baeldung.context.entity.TradeDto;
import com.baeldung.context.service.SecurityService;

@Mapper
public abstract class TradeMapperWithAfterMapping {
    final Logger logger = LoggerFactory.getLogger(TradeMapperWithAfterMapping.class);

    public static TradeMapperWithAfterMapping getInstance() {
        return Mappers.getMapper(TradeMapperWithAfterMapping.class);
    }

    protected abstract TradeDto toTradeDto(Trade trade, @Context String identifierType);

    @AfterMapping
    protected TradeDto convertToIdentifier(Trade trade, @MappingTarget TradeDto tradeDto, @Context String identifierType) {
        logger.info("convertToIdentifier(): Converting to identifier type: {}", identifierType);
        SecurityService securityService = new SecurityService();
        tradeDto.setSecurityIdentifier(securityService.getSecurityIdentifierOfType(trade.getSecurityID(), identifierType));
        return tradeDto;
    }
}