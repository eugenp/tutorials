package com.example.demo;

import static tech.cassandre.trading.bot.dto.position.PositionStatusDTO.CLOSED;
import static tech.cassandre.trading.bot.dto.position.PositionStatusDTO.OPENED;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.BTC;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.USDT;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.cassandre.trading.bot.dto.market.TickerDTO;
import tech.cassandre.trading.bot.dto.position.PositionDTO;
import tech.cassandre.trading.bot.dto.position.PositionRulesDTO;
import tech.cassandre.trading.bot.dto.user.AccountDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPairDTO;
import tech.cassandre.trading.bot.strategy.BasicCassandreStrategy;
import tech.cassandre.trading.bot.strategy.CassandreStrategy;

@CassandreStrategy
public class MyFirstStrategy extends BasicCassandreStrategy {

    private final Logger logger = LoggerFactory.getLogger(MyFirstStrategy.class);

    @Override
    public Set<CurrencyPairDTO> getRequestedCurrencyPairs() {
        return Set.of(new CurrencyPairDTO(BTC, USDT));
    }

    @Override
    public Optional<AccountDTO> getTradeAccount(Set<AccountDTO> accounts) {
        return accounts.stream()
            .filter(a -> "trade".equals(a.getName()))
            .findFirst();
    }

    @Override
    public void onTickerUpdate(TickerDTO ticker) {
        logger.info("Received a new ticker : {}", ticker);
        
        if (new BigDecimal("56000").compareTo(ticker.getLast()) == -1) {

            if (canBuy(new CurrencyPairDTO(BTC, USDT), new BigDecimal("0.01"))) {
                PositionRulesDTO rules = PositionRulesDTO.builder()
                    .stopGainPercentage(4f)
                    .stopLossPercentage(25f)
                    .build();
                createLongPosition(new CurrencyPairDTO(BTC, USDT), new BigDecimal("0.01"), rules);
            }

        }
    }

    @Override
    public void onPositionStatusUpdate(PositionDTO position) {
        if (position.getStatus() == OPENED) {
            logger.info("> New position opened : {}", position.getPositionId());
        }
        if (position.getStatus() == CLOSED) {
            logger.info("> Position closed : {}", position.getDescription());
        }
    }

}
