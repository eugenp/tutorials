package com.baeldung.context.service;

import org.mapstruct.Context;
import org.mapstruct.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.context.entity.Trade;
import com.baeldung.context.entity.TradeDto;

public class TradeFactory {
    private static final Logger logger = LoggerFactory.getLogger(TradeFactory.class);

    @ObjectFactory
    public TradeDto createTradeDto(Trade trade, @Context String identifierType) {
        logger.info("createTradeDto(): Creating TradeDto with identifier type: {}", identifierType);
        SecurityService securityService = new SecurityService();
        String securityIdentifier = securityService.getSecurityIdentifierOfType(trade.getSecurityID(), identifierType);

        return new TradeDto(securityIdentifier, trade.getQuantity(), trade.getPrice());
    }
}


