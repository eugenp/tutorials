package com.baeldung.context.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.context.entity.Trade;
import com.baeldung.context.entity.TradeDto;
import com.baeldung.context.service.SecurityService;

public class MapperContextUnitTest {
    @Test
    void whenGivenSecurityIDInTradeObject_thenSetSedolInTradeDto() {
        Trade trade = createTradeObject();

        TradeDto tradeDto = TradeMapperWithContextValue.getInstance()
            .toTradeDto(trade, "SEDOL");

        assertEquals("B1Y8QX7", tradeDto.getSecurityIdentifier());
    }

    @Test
    void whenGivenSecurityIDInTradeObject_thenSetIsinAttributeInTradeDto() {
        Trade trade = createTradeObject();

        TradeDto tradeDto = TradeMapperWithContextService.getInstance()
            .toTradeDto(trade, new SecurityService());

        assertEquals("US0378331005", tradeDto.getSecurityIdentifier());
    }

    @Test
    void whenGivenSecurityIDInTradeObject_thenSetIsinAttributeInTradeDtoWithIdentifierType() {
        Trade trade = createTradeObject();

        TradeDto tradeDto = TradeMapperWithAfterMapping.getInstance()
            .toTradeDto(trade, "CUSIP");

        assertEquals("037833100", tradeDto.getSecurityIdentifier());
    }

    @Test
    void whenGivenSecurityIDInTradeObject_thenUseObjectFactoryToCreateTradeDto() {
        Trade trade = createTradeObject();

        TradeDto tradeDto = TradeMapperUsingObjectFactory.getInstance()
            .toTradeDto(trade, "SEDOL");

        assertEquals("B1Y8QX7", tradeDto.getSecurityIdentifier());
    }

    private Trade createTradeObject() {
        return new Trade("AAPL", 100, 150.0);
    }

}
