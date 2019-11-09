package com.baeldung.clientrx;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvokerProvider;
import org.glassfish.jersey.client.rx.rxjava2.RxFlowableInvoker;
import org.glassfish.jersey.client.rx.rxjava2.RxFlowableInvokerProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.reactivex.Flowable;
import rx.Observable;

/**
 *
 * @author baeldung
 */
public class ClientOrchestrationIntegrationTest {

    private Client client = ClientBuilder.newClient();

    private WebTarget userIdService = client.target("http://localhost:{port}/id-service/ids");
    private WebTarget nameService = client.target("http://localhost:{port}/name-service/users/{userId}/name");
    private WebTarget hashService = client.target("http://localhost:{port}/hash-service/{rawValue}");

    private Logger logger = LoggerFactory.getLogger(ClientOrchestrationIntegrationTest.class);

    private String expectedUserIds = "[1,2,3,4,5,6]";

    private List<String> expectedNames = Arrays.asList("n/a", "Thor", "Hulk", "BlackWidow", "BlackPanther", "TheTick", "Hawkeye");

    private List<String> expectedHashValues = Arrays.asList("roht1", "kluh2", "WodiwKcalb3", "RehtnapKclab4", "kciteht5", "eyekwah6");

    @Rule
    public WireMockRule wireMockServer = new WireMockRule(0);

    @Before
    public void setup() {

        stubFor(get(urlEqualTo("/id-service/ids")).willReturn(aResponse().withBody(expectedUserIds).withHeader("Content-Type", "application/json")));

        stubFor(get(urlEqualTo("/name-service/users/1/name")).willReturn(aResponse().withBody(expectedNames.get(1))));
        stubFor(get(urlEqualTo("/name-service/users/2/name")).willReturn(aResponse().withBody(expectedNames.get(2))));
        stubFor(get(urlEqualTo("/name-service/users/3/name")).willReturn(aResponse().withBody(expectedNames.get(3))));
        stubFor(get(urlEqualTo("/name-service/users/4/name")).willReturn(aResponse().withBody(expectedNames.get(4))));
        stubFor(get(urlEqualTo("/name-service/users/5/name")).willReturn(aResponse().withBody(expectedNames.get(5))));
        stubFor(get(urlEqualTo("/name-service/users/6/name")).willReturn(aResponse().withBody(expectedNames.get(6))));

        stubFor(get(urlEqualTo("/hash-service/Thor1")).willReturn(aResponse().withBody(expectedHashValues.get(0))));
        stubFor(get(urlEqualTo("/hash-service/Hulk2")).willReturn(aResponse().withBody(expectedHashValues.get(1))));
        stubFor(get(urlEqualTo("/hash-service/BlackWidow3")).willReturn(aResponse().withBody(expectedHashValues.get(2))));
        stubFor(get(urlEqualTo("/hash-service/BlackPanther4")).willReturn(aResponse().withBody(expectedHashValues.get(3))));
        stubFor(get(urlEqualTo("/hash-service/TheTick5")).willReturn(aResponse().withBody(expectedHashValues.get(4))));
        stubFor(get(urlEqualTo("/hash-service/Hawkeye6")).willReturn(aResponse().withBody(expectedHashValues.get(5))));

    }

    @Test
    public void callBackOrchestrate() throws InterruptedException {
        List<String> receivedHashValues = new ArrayList<>();

        final CountDownLatch completionTracker = new CountDownLatch(expectedHashValues.size()); // used to keep track of the progress of the subsequent calls

        getUserIdService().request().accept(MediaType.APPLICATION_JSON).async().get(new InvocationCallback<List<Long>>() {
            @Override
            public void completed(List<Long> employeeIds) {
                logger.info("[CallbackExample] id-service result: {}", employeeIds);
                employeeIds.forEach((id) -> {
                    // for each employee ID, get the name
                    getNameService().resolveTemplate("userId", id).request().async().get(new InvocationCallback<String>() {

                        @Override
                        public void completed(String response) {
                            logger.info("[CallbackExample] name-service result: {}", response);

                            getHashService().resolveTemplate("rawValue", response + id).request().async().get(new InvocationCallback<String>() {
                                @Override
                                public void completed(String response) {
                                    logger.info("[CallbackExample] hash-service result: {}", response);
                                    receivedHashValues.add(response);
                                    completionTracker.countDown();
                                }

                                @Override
                                public void failed(Throwable throwable) {
                                    logger.warn("[CallbackExample] An error has occurred in the hashing request step!", throwable);
                                }
                            });
                        }

                        @Override
                        public void failed(Throwable throwable) {
                            logger.warn("[CallbackExample] An error has occurred in the username request step!", throwable);
                        }
                    });
                });

            }

            @Override
            public void failed(Throwable throwable) {
                logger.warn("[CallbackExample] An error has occurred in the userId request step!", throwable);
            }
        });

        // wait for async calls to complete
        try {
            // wait for inner requests to complete in 10 seconds
            if (!completionTracker.await(10, TimeUnit.SECONDS)) {
                logger.warn("[CallbackExample] Some requests didn't complete within the timeout");
            }
        } catch (InterruptedException e) {
            logger.error("Interrupted!", e);
        }

        assertThat(receivedHashValues).containsAll(expectedHashValues);
    }

    @Test
    public void rxOrchestrate() throws InterruptedException {
        List<String> receivedHashValues = new ArrayList<>();

        final CountDownLatch completionTracker = new CountDownLatch(expectedHashValues.size()); // used to keep track of the progress of the subsequent calls

        CompletionStage<List<Long>> userIdStage = getUserIdService().request().accept(MediaType.APPLICATION_JSON).rx().get(new GenericType<List<Long>>() {
        }).exceptionally((throwable) -> {
            logger.warn("[CompletionStageExample] An error has occurred");
            return null;
        });

        userIdStage.thenAcceptAsync(employeeIds -> {
            logger.info("[CompletionStageExample] id-service result: {}", employeeIds);
            employeeIds.forEach((Long id) -> {
                CompletableFuture<String> completable = getNameService().resolveTemplate("userId", id).request().rx().get(String.class).toCompletableFuture();

                completable.thenAccept((String userName) -> {
                    logger.info("[CompletionStageExample] name-service result: {}", userName);
                    getHashService().resolveTemplate("rawValue", userName + id).request().rx().get(String.class).toCompletableFuture().thenAcceptAsync(hashValue -> {
                        logger.info("[CompletionStageExample] hash-service result: {}", hashValue);
                        receivedHashValues.add(hashValue);
                        completionTracker.countDown();
                    }).exceptionally((throwable) -> {
                        logger.warn("[CompletionStageExample] Hash computation failed for {}", id);
                        completionTracker.countDown();
                        return null;
                    });

                });

            });
        });

        // wait for async calls to complete
        try {
            // wait for inner requests to complete in 10 seconds
            if (!completionTracker.await(10, TimeUnit.SECONDS)) {
                logger.warn("[CallbackExample] Some requests didn't complete within the timeout");
            }
        } catch (InterruptedException e) {
            logger.error("Interrupted!", e);
        }

        assertThat(receivedHashValues).containsAll(expectedHashValues);
    }

    @Test
    public void observableJavaOrchestrate() throws InterruptedException {
        List<String> receivedHashValues = new ArrayList<>();

        final CountDownLatch completionTracker = new CountDownLatch(expectedHashValues.size()); // used to keep track of the progress of the subsequent calls

        Observable<List<Long>> observableUserIdService = getUserIdService().register(RxObservableInvokerProvider.class).request().accept(MediaType.APPLICATION_JSON).rx(RxObservableInvoker.class).get(new GenericType<List<Long>>() {
        }).asObservable();

        observableUserIdService.subscribe((List<Long> employeeIds) -> {
            logger.info("[ObservableExample] id-service result: {}", employeeIds);
            Observable.from(employeeIds).subscribe(id -> getNameService().register(RxObservableInvokerProvider.class).resolveTemplate("userId", id).request().rx(RxObservableInvoker.class).get(String.class).asObservable() // gotten the name for the given
                    // userId
                    .doOnError((throwable) -> {
                        logger.warn("[ObservableExample] An error has occurred in the username request step {}", throwable.getMessage());
                    }).subscribe(userName -> {
                        logger.info("[ObservableExample] name-service result: {}", userName);
                        getHashService().register(RxObservableInvokerProvider.class).resolveTemplate("rawValue", userName + id).request().rx(RxObservableInvoker.class).get(String.class).asObservable() // gotten the hash value for
                                // userId+username
                                .doOnError((throwable) -> {
                                    logger.warn("[ObservableExample] An error has occurred in the hashing request step {}", throwable.getMessage());
                                }).subscribe(hashValue -> {
                            logger.info("[ObservableExample] hash-service result: {}", hashValue);
                            receivedHashValues.add(hashValue);
                            completionTracker.countDown();
                        });
                    }));
        });

        // wait for async calls to complete
        try {
            // wait for inner requests to complete in 10 seconds
            if (!completionTracker.await(10, TimeUnit.SECONDS)) {
                logger.warn("[CallbackExample] Some requests didn't complete within the timeout");
            }
        } catch (InterruptedException e) {
            logger.error("Interrupted!", e);
        }

        assertThat(receivedHashValues).containsAll(expectedHashValues);
    }

    @Test
    public void flowableJavaOrchestrate() throws InterruptedException {
        List<String> receivedHashValues = new ArrayList<>();

        final CountDownLatch completionTracker = new CountDownLatch(expectedHashValues.size()); // used to keep track of the progress of the subsequent calls

        Flowable<List<Long>> userIdFlowable = getUserIdService().register(RxFlowableInvokerProvider.class).request().rx(RxFlowableInvoker.class).get(new GenericType<List<Long>>() {
        });

        userIdFlowable.subscribe((List<Long> employeeIds) -> {
            logger.info("[FlowableExample] id-service result: {}", employeeIds);
            Flowable.fromIterable(employeeIds).subscribe(id -> {
                getNameService().register(RxFlowableInvokerProvider.class).resolveTemplate("userId", id).request().rx(RxFlowableInvoker.class).get(String.class) // gotten the name for the given userId
                        .doOnError((throwable) -> {
                            logger.warn("[FlowableExample] An error has occurred in the username request step {}", throwable.getMessage());
                        }).subscribe(userName -> {
                    logger.info("[FlowableExample] name-service result: {}", userName);
                    getHashService().register(RxFlowableInvokerProvider.class).resolveTemplate("rawValue", userName + id).request().rx(RxFlowableInvoker.class).get(String.class) // gotten the hash value for userId+username
                            .doOnError((throwable) -> {
                                logger.warn(" [FlowableExample] An error has occurred in the hashing request step!", throwable);
                            }).subscribe(hashValue -> {
                        logger.info("[FlowableExample] hash-service result: {}", hashValue);
                        receivedHashValues.add(hashValue);
                        completionTracker.countDown();
                    });
                });
            });
        });

        // wait for async calls to complete
        try {
            // wait for inner requests to complete in 10 seconds
            if (!completionTracker.await(10, TimeUnit.SECONDS)) {
                logger.warn("[CallbackExample] Some requests didn't complete within the timeout");
            }
        } catch (InterruptedException e) {
            logger.error("Interrupted!", e);
        }

        assertThat(receivedHashValues).containsAll(expectedHashValues);
    }

    private int getPort() {
        return wireMockServer.port();
    }

    private WebTarget getUserIdService() {
        return userIdService.resolveTemplate("port", getPort());
    }

    private WebTarget getNameService() {
        return nameService.resolveTemplate("port", getPort());
    }

    private WebTarget getHashService() {
        return hashService.resolveTemplate("port", getPort());
    }

}
