package com.baeldung.hexagonal;

import com.baeldung.hexagonal.domain.persistence.PersistenceService;
import com.baeldung.hexagonal.domain.service.ShortenedUrlService;
import com.baeldung.hexagonal.domain.service.ShortenedUrlServiceImpl;
import com.baeldung.hexagonal.domain.shortener.ShortenerService;
import com.baeldung.hexagonal.server.persistence.h2.H2PersistenceService;
import com.baeldung.hexagonal.server.persistence.h2.H2Repository;
import com.baeldung.hexagonal.server.shortener.FakeShortener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HexagonalArchitectureApplication {

    private static final Logger log = LoggerFactory.getLogger(HexagonalArchitectureApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitectureApplication.class);
    }

    @Bean
    public CommandLineRunner demo(ShortenedUrlService shortenedUrlService) {
        return (args) -> {
            String code = shortenedUrlService.create("http://www.google.com");
            log.info("----------------------------");
            log.info(String.format("http://www.google.com - %s", code));
            log.info("----------------------------");
        };
    }

    @Bean
    public PersistenceService persistenceService(H2Repository h2Repository) {
        return new H2PersistenceService(h2Repository);
    }

    @Bean
    public ShortenerService shortenerService() {
        return new FakeShortener();
    }

    @Bean
    public ShortenedUrlService shortenedUrlService(ShortenerService shortenerService,
                                                   PersistenceService persistenceService) {
        return new ShortenedUrlServiceImpl(shortenerService, persistenceService);
    }

}
