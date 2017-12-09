package com.baeldung.spring.data.reactive.repo;

import com.baeldung.spring.data.reactive.ApplicationConfiguration;
import com.baeldung.spring.data.reactive.domain.Taxi;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rx.Observable;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by shazi on 11/21/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfiguration.class)
public class RxJava2TaxiRepositoryTest {

    @Autowired
    RxJava2TaxiRepository rxJava2TaxiRepository;

    @Autowired
    ReactiveMongoOperations reactiveMongoOperations;

    @Before
    public void setup() {
        reactiveMongoOperations.collectionExists(Taxi.class)
                .flatMap(exists -> exists ? reactiveMongoOperations.dropCollection(Taxi.class) : Mono.just(exists))
                .flatMap(o -> reactiveMongoOperations.createCollection(Taxi.class, CollectionOptions.empty().size(1024 * 1024).maxDocuments(100).capped()))
                .then()
                .block();

        rxJava2TaxiRepository.saveAll(
                Flowable.fromArray(
                        new Taxi(UUID.randomUUID().toString(), "CAL-4259", 4),
                        new Taxi(UUID.randomUUID().toString(), "BCN-8542", 4))).blockingSubscribe();
    }

    @Test
    public void testFindByNumber() {
        List<Taxi> myTaxis = rxJava2TaxiRepository.findByNumber("CAL-4259")
                .toList()
                .blockingGet();

        assertThat(myTaxis)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    public void testFindWithTailableCursorBy() throws Exception {
        Disposable subscription = rxJava2TaxiRepository.findWithTailableCursorBy()
                .doOnNext(System.out::println)
                .doOnComplete(() -> System.out.println("Finished"))
                .doOnTerminate(() -> System.out.println("Terminated"))
                .subscribe();

        Thread.sleep(1000);

        rxJava2TaxiRepository.save(new Taxi(UUID.randomUUID().toString(), "ABC-1234", 4)).subscribe();
        Thread.sleep(100);

        rxJava2TaxiRepository.save(new Taxi(UUID.randomUUID().toString(), "XYZ-1234", 4)).subscribe();
        Thread.sleep(1000);

        subscription.dispose();

        rxJava2TaxiRepository.save(new Taxi(UUID.randomUUID().toString(), "DEF-1234", 4)).subscribe();
        Thread.sleep(100);

    }
}
