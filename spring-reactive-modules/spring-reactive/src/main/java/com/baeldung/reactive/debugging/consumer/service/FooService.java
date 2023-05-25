package com.baeldung.reactive.debugging.consumer.service;

import com.baeldung.reactive.debugging.consumer.model.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static com.baeldung.reactive.debugging.consumer.service.FooReporter.reportResult;

@Component
public class FooService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooService.class);

    public void processFoo(Flux<Foo> flux) {
        flux.map(FooNameHelper::concatFooName)
          .map(FooNameHelper::substringFooName)
          .log()
          .map(FooReporter::reportResult)
          .doOnError(error -> LOGGER.error("The following error happened on processFoo method!", error))
          .subscribe();
    }

    public void processFooInAnotherScenario(Flux<Foo> flux) {
        flux.map(FooNameHelper::substringFooName)
          .map(FooQuantityHelper::divideFooQuantity)
          .subscribe();
    }

    public void processUsingApproachOneWithErrorHandling(Flux<Foo> flux) {
        LOGGER.info("starting approach one w error handling!");

        flux.map(FooNameHelper::concatAndSubstringFooName)
          .map(FooNameHelper::concatAndSubstringFooName)
          .map(FooNameHelper::substringFooName)
          .map(FooQuantityHelper::processFooReducingQuantity)
          .map(FooQuantityHelper::processFooReducingQuantity)
          .map(FooQuantityHelper::processFooReducingQuantity)
          .map(FooReporter::reportResult)
          .doOnError(error -> LOGGER.error("Approach 1 with Error Handling failed!", error))
          .subscribe();
    }

    public void processUsingApproachThree(Flux<Foo> flux) {
        LOGGER.info("starting approach three!");

        flux.map(FooNameHelper::concatAndSubstringFooName)
          .map(foo -> reportResult(foo, "THREE"))
          .doOnError(error -> LOGGER.error("Approach 3 failed!", error))
          .subscribe();
    }

    public void processUsingApproachFourWithCheckpoint(Flux<Foo> flux) {
        LOGGER.info("starting approach four!");

        flux.map(FooNameHelper::concatAndSubstringFooName)
          .checkpoint("CHECKPOINT 1")
          .map(FooNameHelper::concatAndSubstringFooName)
          .map(FooQuantityHelper::divideFooQuantity)
          .checkpoint("CHECKPOINT 2", true)
          .map(foo -> reportResult(foo, "FOUR"))
          .map(FooNameHelper::concatAndSubstringFooName)
          .doOnError(error -> LOGGER.error("Approach 4 failed!", error))
          .subscribe();
    }

    public void processUsingApproachFourWithInitialCheckpoint(Flux<Foo> flux) {
        LOGGER.info("starting approach four!");

        flux.map(FooNameHelper::concatAndSubstringFooName)
          .checkpoint("CHECKPOINT 1", true)
          .map(FooNameHelper::concatAndSubstringFooName)
          .map(FooQuantityHelper::divideFooQuantity)
          .map(foo -> reportResult(foo, "FOUR"))
          .map(FooNameHelper::concatAndSubstringFooName)
          .doOnError(error -> LOGGER.error("Approach 4-2 failed!", error))
          .subscribe();
    }

    public void processUsingApproachFivePublishingToDifferentParallelThreads(Flux<Foo> flux) {
        LOGGER.info("starting approach five-parallel!");

        flux.map(FooNameHelper::concatAndSubstringFooName)
          .publishOn(Schedulers.newParallel("five-parallel-foo"))
          .log()
          .map(FooNameHelper::concatAndSubstringFooName)
          .map(foo -> reportResult(foo, "FIVE-PARALLEL"))
          .publishOn(Schedulers.newSingle("five-parallel-bar"))
          .map(FooNameHelper::concatAndSubstringFooName)
          .doOnError(error -> LOGGER.error("Approach 5-parallel failed!", error))
          .subscribeOn(Schedulers.newParallel("five-parallel-starter"))
          .subscribe();
    }

    public void processUsingApproachFivePublishingToDifferentSingleThreads(Flux<Foo> flux) {
        LOGGER.info("starting approach five-single!");

        flux.log()
          .subscribeOn(Schedulers.newSingle("five-single-starter"))
          .map(FooNameHelper::concatAndSubstringFooName)
          .publishOn(Schedulers.newSingle("five-single-foo"))
          .map(FooNameHelper::concatAndSubstringFooName)
          .map(FooQuantityHelper::divideFooQuantity)
          .map(foo -> reportResult(foo, "FIVE-SINGLE"))
          .publishOn(Schedulers.newSingle("five-single-bar"))
          .map(FooNameHelper::concatAndSubstringFooName)
          .doOnError(error -> LOGGER.error("Approach 5-single failed!", error))
          .subscribe();
    }

}
