package com.baeldung.spring.data.reactive.repo;

import com.baeldung.spring.data.reactive.ApplicationConfiguration;
import com.baeldung.spring.data.reactive.domain.Taxi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by shazi on 11/20/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfiguration.class)
public class ReactiveTaxiRepositoryTest {

    @Autowired
    ReactiveTaxiRepository reactiveTaxiRepository;

    @Autowired
    ReactiveMongoOperations reactiveMongoOperations;

    @Before
    public void setup() {
        reactiveMongoOperations.collectionExists(Taxi.class)
                .flatMap(exists -> exists ? reactiveMongoOperations.dropCollection(Taxi.class) : Mono.just(exists))
                .flatMap(o -> reactiveMongoOperations.createCollection(Taxi.class, CollectionOptions.empty().size(1024 * 1024).maxDocuments(100).capped()))
                .then()
                .block();

        reactiveTaxiRepository.saveAll(
                Flux.just(
                        new Taxi(UUID.randomUUID().toString(), "CAL-4259", 4),
                        new Taxi(UUID.randomUUID().toString(), "BCN-8542", 4)))
                .then()
                .block();
    }

    @Test
    public void testFindByNumber() {
        List<Taxi> myTaxis = reactiveTaxiRepository.findByNumber("CAL-4259")
                .collectList()
                .block();

        assertThat(myTaxis)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    public void testFindWithTailableCursorBy() throws Exception {
        Disposable subscription = reactiveTaxiRepository.findWithTailableCursorBy()
                .doOnNext(System.out::println)
                .doOnComplete(() -> System.out.println("Finished"))
                .doOnTerminate(() -> System.out.println("Terminated"))
                .subscribe();

        Thread.sleep(1000);

        reactiveTaxiRepository.save(new Taxi(UUID.randomUUID().toString(), "ABC-1234", 4)).subscribe();
        Thread.sleep(100);

        reactiveTaxiRepository.save(new Taxi(UUID.randomUUID().toString(), "XYZ-1234", 4)).subscribe();
        Thread.sleep(1000);

        subscription.dispose();

        reactiveTaxiRepository.save(new Taxi(UUID.randomUUID().toString(), "DEF-1234", 4)).subscribe();
        Thread.sleep(100);

    }
}
