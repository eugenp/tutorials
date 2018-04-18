package com.baeldung;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class WebClientDemoApplication {

    private static Logger logger = Logger.getLogger(WebClientDemoApplication.class);
    public List<Crypto> cryptoList = new ArrayList<>();
    public List<String> cryptoNames = Arrays.asList("Litecoin,Bitcoin,Ripple".split(","));

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            createRandomCryptoStock();
            cryptoList.forEach(System.out::println);
        };
    }

        @Service
        class CryptoService {
                Flux<Crypto> getCryptos() {
                        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
                        interval.subscribe((i) -> cryptoList
                            .forEach(crypto -> crypto.setPrice(changePrice(crypto.getPrice()))));

                        Flux<Crypto> cryptoFlux = Flux.fromStream(Stream.generate(() -> getRandomCrypto()));
                        return Flux.zip(interval, cryptoFlux).map(Tuple2::getT2);
                }
        }

    public static void main(String[] args) {
        SpringApplication.run(WebClientDemoApplication.class, args);
        WebClient webClient = WebClient.create();
        webClient.get()
            .uri("http://localhost:8080/crypto/transaction")
            .accept(MediaType.valueOf(MediaType.APPLICATION_STREAM_JSON_VALUE))
            .retrieve()
            .bodyToFlux(Crypto.class)
            .subscribe(crypto -> logger.info(crypto.name + crypto.price));
    }

    void createRandomCryptoStock() {
        cryptoNames.forEach(cryptoName -> {
            cryptoList.add(new Crypto(cryptoName, generateRandomCryptoPrice()));
        });
    }

    float generateRandomCryptoPrice() {
        float min = 30;
        float max = 50;
        return min + roundFloat(new Random().nextFloat() * (max - min));
    }

    float changePrice(float price) {
        return roundFloat(Math.random() >= 0.5 ? price * 1.05f : price * 0.95f);
    }

    public List<Crypto> getCryptoList() {
        return cryptoList;
    }

    public List<String> getCryptoNames() {
        return cryptoNames;
    }

    Crypto getRandomCrypto() {
        return cryptoList.get(new Random().nextInt(cryptoList.size()));
    }

    float roundFloat(float number) {
        return Math.round(number * 100.0) / 100.0f;
    }
}
