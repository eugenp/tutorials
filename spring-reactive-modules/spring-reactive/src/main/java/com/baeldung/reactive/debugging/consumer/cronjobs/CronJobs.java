package com.baeldung.reactive.debugging.consumer.cronjobs;

import com.baeldung.reactive.debugging.consumer.model.Foo;
import com.baeldung.reactive.debugging.consumer.model.FooDto;
import com.baeldung.reactive.debugging.consumer.service.FooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class CronJobs {

    final Logger logger = LoggerFactory.getLogger(CronJobs.class);
    final WebClient client = WebClient.create("http://localhost:8081");

    @Autowired
    private FooService service;

    @Scheduled(fixedRate = 10000)
    public void consumeInfiniteFlux() {
        Flux<Foo> fluxFoo = client.get()
          .uri("/functional-reactive/periodic-foo")
          .accept(MediaType.TEXT_EVENT_STREAM)
          .retrieve()
          .bodyToFlux(FooDto.class)
          .delayElements(Duration.ofMillis(100))
          .map(dto -> {
              logger.debug("process 1 with dto id {} name{}", dto.getId(), dto.getName());
              return new Foo(dto);
          });

        int random = ThreadLocalRandom.current()
          .nextInt(0, 3);

        switch (random) {
        case 0:
            logger.info("process 1 with approach 1");
            service.processFoo(fluxFoo);
            break;
        case 1:
            logger.info("process 1 with approach 1 EH");
            service.processUsingApproachOneWithErrorHandling(fluxFoo);
            break;
        default:
            logger.info("process 1 with approach 2");
            service.processFooInAnotherScenario(fluxFoo);
            break;
        }
    }

    @Scheduled(fixedRate = 20000)
    public void consumeFiniteFlux2() {
        Flux<Foo> fluxFoo = client.get()
          .uri("/functional-reactive/periodic-foo-2")
          .accept(MediaType.TEXT_EVENT_STREAM)
          .retrieve()
          .bodyToFlux(FooDto.class)
          .delayElements(Duration.ofMillis(100))
          .map(dto -> {
              logger.debug("process 2 with dto id {} name{}", dto.getId(), dto.getName());
              return new Foo(dto);
          });

        int random = ThreadLocalRandom.current()
          .nextInt(0, 3);

        switch (random) {
        case 0:
            logger.info("process 2 with approach 1");
            service.processFoo(fluxFoo);
            break;
        case 1:
            logger.info("process 2 with approach 1 EH");
            service.processUsingApproachOneWithErrorHandling(fluxFoo);
            break;
        default:
            logger.info("process 2 with approach 2");
            service.processFooInAnotherScenario(fluxFoo);
            break;
        }
    }

    @Scheduled(fixedRate = 20000)
    public void consumeFiniteFlux3() {
        Flux<Foo> fluxFoo = client.get()
          .uri("/functional-reactive/periodic-foo-2")
          .accept(MediaType.TEXT_EVENT_STREAM)
          .retrieve()
          .bodyToFlux(FooDto.class)
          .delayElements(Duration.ofMillis(100))
          .map(dto -> {
              logger.debug("process 3 with dto id {} name{}", dto.getId(), dto.getName());
              return new Foo(dto);
          });
        logger.info("process 3 with approach 3");
        service.processUsingApproachThree(fluxFoo);
    }

    @Scheduled(fixedRate = 20000)
    public void consumeFiniteFluxWithCheckpoint4() {
        Flux<Foo> fluxFoo = client.get()
          .uri("/functional-reactive/periodic-foo-2")
          .accept(MediaType.TEXT_EVENT_STREAM)
          .retrieve()
          .bodyToFlux(FooDto.class)
          .delayElements(Duration.ofMillis(100))
          .map(dto -> {
              logger.debug("process 4 with dto id {} name{}", dto.getId(), dto.getName());
              return new Foo(dto);
          });
        logger.info("process 4 with approach 4");
        service.processUsingApproachFourWithCheckpoint(fluxFoo);
    }

    @Scheduled(fixedRate = 20000)
    public void consumeFiniteFluxWitParallelScheduler() {
        Flux<Foo> fluxFoo = client.get()
          .uri("/functional-reactive/periodic-foo-2")
          .accept(MediaType.TEXT_EVENT_STREAM)
          .retrieve()
          .bodyToFlux(FooDto.class)
          .delayElements(Duration.ofMillis(100))
          .map(dto -> {
              logger.debug("process 5-parallel with dto id {} name{}", dto.getId(), dto.getName());
              return new Foo(dto);
          });
        logger.info("process 5-parallel with approach 5-parallel");
        service.processUsingApproachFivePublishingToDifferentParallelThreads(fluxFoo);
    }

    @Scheduled(fixedRate = 20000)
    public void consumeFiniteFluxWithSingleSchedulers() {
        Flux<Foo> fluxFoo = client.get()
          .uri("/functional-reactive/periodic-foo-2")
          .accept(MediaType.TEXT_EVENT_STREAM)
          .retrieve()
          .bodyToFlux(FooDto.class)
          .delayElements(Duration.ofMillis(100))
          .map(dto -> {
              logger.debug("process 5-single with dto id {} name{}", dto.getId(), dto.getName());
              return new Foo(dto);
          });
        logger.info("process 5-single with approach 5-single");
        service.processUsingApproachFivePublishingToDifferentSingleThreads(fluxFoo);
    }
}
