package com.baeldung.reactive.kafka.stream.binder.repository;

import com.baeldung.reactive.kafka.stream.binder.domain.StockUpdate;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;

public interface ClickHouseRepository {

    static ClickHouseRepository create(ConnectionFactory connectionFactory) {
        return () -> Mono.from(connectionFactory.create());
    }

    Mono<Connection> create();

    default Mono<Void> initDatabase() {
        Resource resource = new ClassPathResource("init.sql");
        return readFile(resource).flatMap(this::runScript);
    }

    private Mono<String> readFile(Resource resource) {
        final var dataBufferFlux = DataBufferUtils.read(
                resource,
                new DefaultDataBufferFactory(),
                1024
        );

        return dataBufferFlux.reduce("", (data, buffer) -> {
            String content = buffer.toString(StandardCharsets.UTF_8);
            DataBufferUtils.release(buffer);
            return data + content;
        });
    }

    default Mono<Void> runScript(String scriptContent) {
        return Mono.usingWhen(
                create(),
                connection -> executeScript(connection, scriptContent),
                Connection::close
        );
    }

    private Mono<Void> executeScript(Connection connection, String scriptContent) {
        return Flux.fromIterable(Arrays.stream(scriptContent.split(";")).toList())
                .map(String::trim)
                .filter(statement -> !statement.isEmpty())
                .concatMap(statement -> connection.createStatement(statement).execute())
                .then();
    }

    default Mono<Boolean> saveStockPrice(StockUpdate stockUpdate) {
        return create()
                .flatMapMany(connection -> connection.createStatement("INSERT INTO stock_prices (symbol, original_price, currency, timestamp) VALUES (:symbol, :original_price, :currency, parseDateTime64BestEffort(:timestamp, 9))")
                        .bind("symbol", stockUpdate.symbol())
                        .bind("original_price", stockUpdate.price())
                        .bind("currency", stockUpdate.currency())
                        .bind("timestamp", stockUpdate.timestamp().toString())
                        .execute())
                .flatMap(Result::getRowsUpdated)
                .reduce(Long::sum)
                .map(count -> count > 0);

    }

    default Flux<StockUpdate> findMinuteAvgStockPrices(Instant from, Instant to) {
        return create()
                .flatMapMany(connection -> connection.createStatement("""
                            SELECT
                                symbol,
                                currency,
                                date_time,
                                avgMerge(avg_price) AS avg_price
                            FROM avg_stock_prices
                            WHERE date_time BETWEEN parseDateTime64BestEffort(:from, 9) AND parseDateTime64BestEffort(:to, 9)
                            GROUP BY symbol, currency, date_time;
                    """)
                        .bind("from", from.toString())
                        .bind("to", to.toString())
                        .execute())
                .flatMap(result -> result.map((row, rowMetadata) -> new StockUpdate(
                        row.get("symbol", String.class),
                        row.get("avg_price", Double.class),
                        row.get("currency", String.class),
                        row.get("date_time", Instant.class)
                )));
    }
}
