package com.baeldung.reactive.debugging.consumer.service;

import com.baeldung.reactive.debugging.consumer.model.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static com.baeldung.reactive.debugging.consumer.service.FooNameHelper.concatAndSubstringFooName;
import static com.baeldung.reactive.debugging.consumer.service.FooNameHelper.substringFooName;
import static com.baeldung.reactive.debugging.consumer.service.FooQuantityHelper.divideFooQuantity;
import static com.baeldung.reactive.debugging.consumer.service.FooQuantityHelper.processFooReducingQuantity;
import static com.baeldung.reactive.debugging.consumer.service.FooReporter.reportResult;

@Component
public class FooService {

    private static Logger logger = LoggerFactory.getLogger(FooService.class);

    public void processFoo(Flux<Foo> flux) {
        flux = FooNameHelper.concatFooName(flux);
        flux = FooNameHelper.substringFooName(flux);
        flux = flux.log();
        flux = FooReporter.reportResult(flux);
        flux = flux.doOnError(error -> {
            logger.error("The following error happened on processFoo method!", error);
        });
        flux.subscribe();
    }

    public void processFooInAnotherScenario(Flux<Foo> flux) {
        flux = FooNameHelper.substringFooName(flux);
        flux = FooQuantityHelper.divideFooQuantity(flux);
        flux.subscribe();
    }

    public void processUsingApproachOneWithErrorHandling(Flux<Foo> flux) {
        logger.info("starting approach one w error handling!");
        flux = concatAndSubstringFooName(flux);
        flux = concatAndSubstringFooName(flux);
        flux = substringFooName(flux);
        flux = processFooReducingQuantity(flux);
        flux = processFooReducingQuantity(flux);
        flux = processFooReducingQuantity(flux);
        flux = reportResult(flux, "ONE w/ EH");
        flux = flux.doOnError(error -> {
            logger.error("Approach 1 with Error Handling failed!", error);
        });
        flux.subscribe();
    }

    public void processUsingApproachThree(Flux<Foo> flux) {
        logger.info("starting approach three!");
        flux = concatAndSubstringFooName(flux);
        flux = reportResult(flux, "THREE");
        flux = flux.doOnError(error -> {
            logger.error("Approach 3 failed!", error);
        });
        flux.subscribe();
    }

    public void processUsingApproachFourWithCheckpoint(Flux<Foo> flux) {
        logger.info("starting approach four!");
        flux = concatAndSubstringFooName(flux);
        flux = flux.checkpoint("CHECKPOINT 1");
        flux = concatAndSubstringFooName(flux);
        flux = divideFooQuantity(flux);
        flux = flux.checkpoint("CHECKPOINT 2", true);
        flux = reportResult(flux, "FOUR");
        flux = concatAndSubstringFooName(flux).doOnError(error -> {
            logger.error("Approach 4 failed!", error);
        });
        flux.subscribe();
    }

    public void processUsingApproachFourWithInitialCheckpoint(Flux<Foo> flux) {
        logger.info("starting approach four!");
        flux = concatAndSubstringFooName(flux);
        flux = flux.checkpoint("CHECKPOINT 1", true);
        flux = concatAndSubstringFooName(flux);
        flux = divideFooQuantity(flux);
        flux = reportResult(flux, "FOUR");
        flux = flux.doOnError(error -> {
            logger.error("Approach 4-2 failed!", error);
        });
        flux.subscribe();
    }

    public void processUsingApproachFivePublishingToDifferentParallelThreads(Flux<Foo> flux) {
        logger.info("starting approach five-parallel!");
        flux = concatAndSubstringFooName(flux).publishOn(Schedulers.newParallel("five-parallel-foo"))
            .log();
        flux = concatAndSubstringFooName(flux);
        flux = divideFooQuantity(flux);
        flux = reportResult(flux, "FIVE-PARALLEL").publishOn(Schedulers.newSingle("five-parallel-bar"));
        flux = concatAndSubstringFooName(flux).doOnError(error -> {
            logger.error("Approach 5-parallel failed!", error);
        });
        flux.subscribeOn(Schedulers.newParallel("five-parallel-starter"))
            .subscribe();
    }

    public void processUsingApproachFivePublishingToDifferentSingleThreads(Flux<Foo> flux) {
        logger.info("starting approach five-single!");
        flux = flux.log()
            .subscribeOn(Schedulers.newSingle("five-single-starter"));
        flux = concatAndSubstringFooName(flux).publishOn(Schedulers.newSingle("five-single-foo"));
        flux = concatAndSubstringFooName(flux);
        flux = divideFooQuantity(flux);
        flux = reportResult(flux, "FIVE-SINGLE").publishOn(Schedulers.newSingle("five-single-bar"));
        flux = concatAndSubstringFooName(flux).doOnError(error -> {
            logger.error("Approach 5-single failed!", error);
        });
        flux.subscribe();
    }

}
