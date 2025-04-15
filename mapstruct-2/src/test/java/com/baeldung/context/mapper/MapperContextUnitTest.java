package com.baeldung.context.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.context.entity.Trade;
import com.baeldung.context.entity.TradeDto;

public class MapperContextUnitTest {
    @Test
    void givenBeforeMappingMethod_whenSecurityIdInTradeObject_thenSetSecurityIdentifierInTradeDto() {
        Trade trade = createTradeObject();

        TradeDto tradeDto = TradeMapperWithBeforeMapping.getInstance()
            .toTradeDto(trade, "CUSIP", 6464);

        assertEquals("037833100", tradeDto.getSecurityIdentifier());
    }

    @Test
    void givenAfterMappingMethod_whenSecurityIdInTradeObject_thenSetSecurityIdentifierInTradeDto() {
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
