package com.baeldung.reactivebackpressuredemo.client;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactivebackpressuredemo.model.Foo;

import reactor.core.Disposable;
import reactor.core.scheduler.Schedulers;

public class FooWebClient {

    private WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:8080")
        .build();

    public Disposable newFooResourceDetected() {
        AtomicInteger counter = new AtomicInteger(0);
        return webClient.get()
            .uri("/foo")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .publishOn(Schedulers.single())
            .flatMapMany(response -> response.bodyToFlux(Foo.class))
            .delayElements(Duration.ofMillis(2000))
            .subscribe(s -> {
                System.out.println(counter.incrementAndGet() + " >>>>>>>>>>>> " + s);
            }, err -> System.out.println("Error on Foo Stream: " + err), () -> System.out.println("Foo stream stoped!"));
    }

    /**
     * Used for development reasons only 
     * To run the sample, can be done by: mvn test (SpringBootTest) 
     */
    public static void main(String[] args) {
        FooWebClient fooWebClient = new FooWebClient();
        Disposable disposable = fooWebClient.newFooResourceDetected();
        try {
            Thread.sleep(32000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }

}
