package com.example.demo;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.cassandre.trading.bot.dto.position.PositionStatusDTO.OPENED;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.USDT;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import tech.cassandre.trading.bot.dto.util.CurrencyDTO;
import tech.cassandre.trading.bot.dto.util.GainDTO;
import tech.cassandre.trading.bot.test.mock.TickerFluxMock;

@SpringBootTest
@Import(TickerFluxMock.class)
@DisplayName("Simple strategy test")
public class MyFirstStrategyTest {

    @Autowired
    private MyFirstStrategy strategy;

    @Autowired
    private TickerFluxMock tickerFluxMock;

    @Test
    @DisplayName("Check gains")
    public void whenTickersArrives_checkGains() {
        await().forever().until(() -> tickerFluxMock.isFluxDone());

        final HashMap<CurrencyDTO, GainDTO> gains = strategy.getGains();

        System.out.println("Cumulated gains:");
        gains.forEach((currency, gain) -> System.out.println(currency + " : " + gain.getAmount()));

        System.out.println("Position still opened :");
        strategy.getPositions()
                .values()
                .stream()
                .filter(p -> p.getStatus().equals(OPENED))
                .forEach(p -> System.out.println(" - " + p.getDescription()));

        assertTrue(gains.get(USDT).getPercentage() > 0);
    }
    
}
