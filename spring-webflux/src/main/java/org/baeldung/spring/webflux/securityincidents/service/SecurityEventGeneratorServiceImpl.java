package org.baeldung.spring.webflux.securityincidents.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.baeldung.spring.webflux.securityincidents.domain.SecurityEvent;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import reactor.core.publisher.Flux;

@Service
public class SecurityEventGeneratorServiceImpl implements SecurityEventGeneratorService {

    private final Random random = new Random();

    @Override
    public Flux<SecurityEvent> getSecurityEventStream() {

        return Flux.interval(Duration.ofSeconds(1L))
            .onBackpressureDrop()
            .map(this::createSecurityEventList)
            .flatMapIterable(securityEvents -> securityEvents)
            .log("Generated new event");
    }

    private List<SecurityEvent> createSecurityEventList(Long id) {
        return Arrays.asList(new SecurityEvent[] { new SecurityEvent(randomEvent()) });
    }

    private String randomEvent() {
        List<String> events = Arrays.asList("Port scan", "Suspicious UDP", "TCP fragmentation", "ICMP", "Bogus traffic");
        return events.get(random.nextInt(events.size())) + " from " + generateIp();
    }

    private String generateIp() {
        return (random.nextInt(254) + 1) + "." + (random.nextInt(254) + 1) + "." + (random.nextInt(254) + 1) + "." + (random.nextInt(254) + 1);
    }

}
