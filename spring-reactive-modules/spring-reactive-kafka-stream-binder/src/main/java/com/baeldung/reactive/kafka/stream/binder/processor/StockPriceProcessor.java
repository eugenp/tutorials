package com.baeldung.reactive.kafka.stream.binder.processor;

import com.baeldung.reactive.kafka.stream.binder.clickhouse.ClickHouseRepository;
import com.baeldung.reactive.kafka.stream.binder.currency.CurrencyRate;
import com.baeldung.reactive.kafka.stream.binder.domain.StockUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class StockPriceProcessor {

    @Bean
    public Function<Flux<Message<StockUpdate>>, Flux<Message<StockUpdate>>> processStockPrices(ClickHouseRepository repository, CurrencyRate currencyRate) {
        return stockPrices -> stockPrices.flatMapSequential(message -> {
            StockUpdate stockUpdate = message.getPayload();
            return repository.saveStockPrice(stockUpdate)
                    .flatMap(success -> Boolean.TRUE.equals(success) ? Mono.just(stockUpdate) : Mono.empty())
                    .flatMap(stock -> currencyRate.convertRate("USD", "EUR", stock.price()))
                            .map(newPrice -> convertPrice(stockUpdate, newPrice))
                            .map(priceInEuro -> MessageBuilder.withPayload(priceInEuro)
                                    .setHeader(KafkaHeaders.KEY, stockUpdate.symbol())
                                    .copyHeaders(message.getHeaders())
                                    .build());
        });
    }

    private StockUpdate convertPrice(StockUpdate stockUpdate, double newPrice) {
        return new StockUpdate(stockUpdate.symbol(), newPrice, stockUpdate.timestamp());
    }

}
