package com.baeldung.samples.jerseyrx;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvokerProvider;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import org.glassfish.jersey.client.rx.rxjava2.RxFlowableInvoker;
import org.glassfish.jersey.client.rx.rxjava2.RxFlowableInvokerProvider;
import rx.Observable;

/**
 *
 * @author baeldung
 */
public class ClientOrchestration {

    Client client = ClientBuilder.newClient();
    WebTarget userIdService = client.target("http://localhost:8080/serviceA/id?limit=10");
    WebTarget nameService = client.target("http://localhost:8080/serviceA/{empId}/name");
    WebTarget hashService = client.target("http://localhost:8080/serviceA/{comboIDandName}/hash");

    Logger logger = Logger.getLogger("ClientOrchestrator");

    public static void main(String[] args) {
        ClientOrchestration orchestrator = new ClientOrchestration();

        orchestrator.callBackOrchestrate();
        orchestrator.rxOrchestrate();
        orchestrator.observableJavaOrchestrate();
        orchestrator.flowableJavaOrchestrate();

    }

    public void callBackOrchestrate() {
        logger.info("Orchestrating with the pyramid of doom");
        userIdService.request()
                .async()
                .get(new InvocationCallback<List<Long>>() {
                    @Override
                    public void completed(List<Long> empIds) {
                        CountDownLatch completionTracker = new CountDownLatch(empIds.size()); //used to keep track of the progress of the subsequent calls
                        empIds.forEach((id) -> {
                            //for each employee ID, get the name
                            nameService.resolveTemplate("empId", id).request()
                                    .async()
                                    .get(new InvocationCallback<String>() {
                                        @Override
                                        public void completed(String response) {
                                            completionTracker.countDown();
                                            hashService.request().async().get(new InvocationCallback<String>() {
                                                @Override
                                                public void completed(String response) {
                                                    logger.log(Level.INFO, "The hash output {0}", response);
                                                }

                                                @Override
                                                public void failed(Throwable throwable) {
                                                    completionTracker.countDown();
                                                    logger.log(Level.WARNING, "An error has occurred in the hashing request step {0}", throwable.getMessage());
                                                }
                                            });
                                        }

                                        @Override
                                        public void failed(Throwable throwable) {
                                            completionTracker.countDown();
                                            logger.log(Level.WARNING, "An error has occurred in the username request step {0}", throwable.getMessage());
                                        }
                                    });
                        });

                        try {
                            if (!completionTracker.await(10, TimeUnit.SECONDS)) { //wait for inner requests to complete in 10 seconds
                                logger.warning("Some requests didn't complete within the timeout");
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ClientOrchestration.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                    @Override
                    public void failed(Throwable throwable) {
                        //implement callback
                    }
                });
    }

    public void rxOrchestrate() {
        logger.info("Orchestrating with a CompletionStage");
        CompletionStage<List<Long>> userIdStage = userIdService.request()
                .rx()
                .get(new GenericType<List<Long>>() {
                })
                .exceptionally((Throwable throwable) -> {
                    logger.warning("An error has occurred");
                    return null;
                });

        userIdStage.thenAcceptAsync(listOfIds -> {
            listOfIds.stream().forEach((Long id) -> {
                CompletableFuture<String> completable = nameService.resolveTemplate("empId", id)
                        .request()
                        .rx()
                        .get(String.class)
                        .toCompletableFuture();

                completable.thenAccept((String userName) -> {
                    hashService.resolveTemplate("comboIDandName", userName + id)
                            .request()
                            .rx()
                            .get(String.class)
                            .toCompletableFuture()
                            .thenAcceptAsync(hashValue -> logger.log(Level.INFO, "The hash output {0}", hashValue))
                            .exceptionally((Throwable throwable) -> {
                                logger.log(Level.WARNING, "Hash computation failed for {0}", id);
                                return null;
                            });

                });

            });
        });

    }

    public void observableJavaOrchestrate() {

        logger.info("Orchestrating with Observables");
        Observable<List<Long>> userIdObservable = userIdService.register(RxObservableInvokerProvider.class).request()
                .rx(RxObservableInvoker.class)
                .get(new GenericType<List<Long>>() {
                });

        userIdObservable.subscribe((List<Long> listOfIds) -> {
            Observable.from(listOfIds).map(id
                    -> nameService.resolveTemplate("empId", id)
                            .request()
                            .rx(RxObservableInvoker.class)
                            .get(String.class)
                            .asObservable() //gotten the name for the given empId
                            .doOnError(throwable -> logger.log(Level.WARNING, "An error has occurred in the username request step {0}", throwable.getMessage()))
                            .subscribe(userName -> hashService.resolveTemplate("comboIDandName", userName + id)
                            .request()
                            .rx(RxObservableInvoker.class)
                            .get(String.class)
                            .asObservable() //gotten the hash value for empId+username
                            .doOnError(throwable -> logger.log(Level.WARNING, "An error has occurred in the hashing request step {0}", throwable.getMessage()))
                            .subscribe(hashValue -> logger.log(Level.INFO, "The hash output {0}", hashValue))));
        });

    }

    public void flowableJavaOrchestrate() {

        logger.info("Orchestrating with Flowable");
        Flowable<List<Long>> userIdObservable = userIdService.register(RxFlowableInvokerProvider.class).request()
                .rx(RxFlowableInvoker.class)
                .get(new GenericType<List<Long>>() {
                });

        Disposable subscribe = userIdObservable.subscribe((List<Long> listOfIds) -> {
            Observable.from(listOfIds).map(id
                    -> nameService.resolveTemplate("empId", id)
                            .request()
                            .rx(RxObservableInvoker.class)
                            .get(String.class)
                            .asObservable() //gotten the name for the given empId
                            .doOnError(throwable -> logger.log(Level.WARNING, "An error has occurred in the username request step {0}", throwable.getMessage()))
                            .subscribe(userName -> hashService.resolveTemplate("comboIDandName", userName + id)
                            .request()
                            .rx(RxObservableInvoker.class)
                            .get(String.class)
                            .asObservable() //gotten the hash value for empId+username
                            .doOnError(throwable -> logger.warning("An error has occurred in the hashing request step " + throwable.getMessage()))
                            .subscribe(hashValue -> logger.log(Level.INFO, "The hash output {0}", hashValue))));
        });

    }

}
