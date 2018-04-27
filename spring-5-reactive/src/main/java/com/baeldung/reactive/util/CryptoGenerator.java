package com.baeldung.reactive.util;

import com.baeldung.reactive.model.CryptoCurrency;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class CryptoGenerator {

    private final MathContext mathContext = new MathContext(2);

    private final Random random = new Random();

    private final List<CryptoCurrency> cryptoList = new ArrayList<>();

    /**
     * Create initial crypto currency objects
     */
    public CryptoGenerator() {
        this.cryptoList.add(new CryptoCurrency("BTC", 82.26));
        this.cryptoList.add(new CryptoCurrency("ETH", 63.74));
        this.cryptoList.add(new CryptoCurrency("ICX", 847.24));
        this.cryptoList.add(new CryptoCurrency("BCC", 65.11));
    }


    /**
     * This mehtod create a indefinite reactive stream of Crypto market price updates
     *
     * @param duration
     * @return Reactive Stream of CryptoCurrency
     */
    public Flux<CryptoCurrency> fetchCryptoStream(Duration duration) {

        return Flux.interval(duration)
                .onBackpressureDrop()
                .map(this::generateCryptoPrice)
                .flatMapIterable(crypto -> crypto)
                .log("com.baeldung.reactive.cryptoTrade");
    }

    /**
     * This method update the price and returns the list of CryptoCurrency objects
     *
     * @param interval
     * @return List of CryptoCurrency generated objects
     */
    private List<CryptoCurrency> generateCryptoPrice(long interval) {
        final Instant instant = Instant.now();
        return cryptoList.stream()
                .map(baseCrypto -> {
                    BigDecimal priceChange = baseCrypto.getPrice()
                            .multiply(
                                    new BigDecimal(0.05 * this.random.nextDouble()),
                                    this.mathContext
                            );
                    CryptoCurrency result =
                            new CryptoCurrency(
                                    baseCrypto.getName(),
                                    baseCrypto.getPrice().add(priceChange));
                    result.setInstant(instant);
                    return result;
                })
                .collect(Collectors.toList());
    }

}