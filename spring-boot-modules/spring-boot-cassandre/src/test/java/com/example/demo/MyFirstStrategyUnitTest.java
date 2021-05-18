package com.example.demo;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tech.cassandre.trading.bot.dto.position.PositionStatusDTO.OPENED;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.USDT;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import tech.cassandre.trading.bot.dto.util.CurrencyDTO;
import tech.cassandre.trading.bot.dto.util.GainDTO;
import tech.cassandre.trading.bot.test.mock.TickerFluxMock;

@SpringBootTest
@Import(TickerFluxMock.class)
@DisplayName("Simple strategy test")
public class MyFirstStrategyUnitTest {

    private final Logger logger = LoggerFactory.getLogger(MyFirstStrategyUnitTest.class);

    @Autowired
    private MyFirstStrategy strategy;

    @Autowired
    private TickerFluxMock tickerFluxMock;

    @Test
    @DisplayName("Check gains")
    public void whenTickersArrives_thenCheckGains() {
        await().forever().until(() -> tickerFluxMock.isFluxDone());

        final HashMap<CurrencyDTO, GainDTO> gains = strategy.getGains();

        logger.info("Cumulated gains:");
        gains.forEach((currency, gain) -> logger.info(currency + " : " + gain.getAmount()));

        logger.info("Position still opened :");
        strategy.getPositions()
                .values()
                .stream()
                .filter(p -> p.getStatus().equals(OPENED))
                .forEach(p -> logger.info(" - {} " + p.getDescription()));

        assertTrue(gains.get(USDT).getPercentage() > 0);
    }
    
}
