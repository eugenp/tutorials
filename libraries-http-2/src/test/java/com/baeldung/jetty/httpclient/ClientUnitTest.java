package com.baeldung.jetty.httpclient;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.reactive.client.ReactiveRequest;
import org.eclipse.jetty.reactive.client.ReactiveRequest.Event.Type;
import org.eclipse.jetty.reactive.client.ReactiveResponse;
import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.reactivex.Flowable;
import io.reactivex.Single;
import reactor.core.publisher.Mono;

public class ClientUnitTest {

    private static final String CONTENT = "Hello World!";
    private static final String URI = "http://localhost:8080";
    private static HttpClient httpClient;
    private static Server server;
    private final Logger logger = LoggerFactory.getLogger(ClientUnitTest.class);

    @BeforeClass
    public static void init() {
        server = new Server(8080);
        server.setHandler(new RequestHandler());
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        httpClient = new HttpClient();
        try {
            httpClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ReactiveStreams
   // @Test
    public void givenReactiveClient_whenReactiveStreamsRequested_shouldReturn200() throws Exception {

        Request request = httpClient.newRequest(URI);
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request)
            .build();
        Publisher<ReactiveResponse> publisher = reactiveRequest.response();

        BlockingSubscriber subscriber = new BlockingSubscriber();
        publisher.subscribe(subscriber);
        ReactiveResponse response = subscriber.block();
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK_200);
    }

    // ProjectReactor
   // @Test
    public void givenReactiveClient_whenProjectReactorRequested_shouldReturn200() throws Exception {

        Request request = httpClient.newRequest(URI);
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request)
            .build();
        Publisher<ReactiveResponse> publisher = reactiveRequest.response();

        ReactiveResponse response = Mono.from(publisher)
            .block();

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus(), HttpStatus.OK_200);
    }

    // SpringWebFlux
    @Test
    public void givenReactiveClient_whenRequested_shouldReturnResponse() throws Exception {// forbidden
        logger.info("starting givenReactiveClient_whenRequested_shouldReturnResponse");
        ClientHttpConnector clientConnector = new JettyClientHttpConnector(httpClient);
        WebClient client = WebClient.builder()
            .clientConnector(clientConnector)
            .build();
        logger.info("got webclient");
        String responseContent = client.post()
            .uri(URI)
            .contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromPublisher(Mono.just(CONTENT), String.class))
            .retrieve()
            .bodyToMono(String.class)
            .block();
        logger.info("responseContent = " + responseContent);
        Assert.assertNotNull(responseContent);
        Assert.assertEquals(CONTENT, responseContent);
    }

    // RxJava2
   // @Test
    public void givenReactiveClient_whenRequestedWithBody_ShouldReturnBody() throws Exception {// org.junit.ComparisonFailure
        Request request = httpClient.newRequest(URI);
        ReactiveRequest reactiveRequest = ReactiveRequest.newBuilder(request)
            .content(ReactiveRequest.Content.fromString(CONTENT, MediaType.TEXT_PLAIN_VALUE, StandardCharsets.UTF_16))
            .build();
        Publisher<String> publisher = reactiveRequest.response(ReactiveResponse.Content.asString());

        String responseContent = Single.fromPublisher(publisher)
            .blockingGet();

        Assert.assertEquals(CONTENT, responseContent);
    }

   // @Test
    public void givenReactiveClient_whenRequested_ShouldPrintEvents() throws Exception {
        ReactiveRequest request = ReactiveRequest.newBuilder(httpClient, URI)
            .content(ReactiveRequest.Content.fromString(CONTENT, MediaType.TEXT_PLAIN_VALUE, UTF_8))
            .build();
        Publisher<ReactiveRequest.Event> requestEvents = request.requestEvents();
        Publisher<ReactiveResponse.Event> responseEvents = request.responseEvents();

        List<Type> requestEventTypes = new ArrayList<>();
        List<ReactiveResponse.Event.Type> responseEventTypes = new ArrayList<>();

        Flowable.fromPublisher(requestEvents)
            .map(ReactiveRequest.Event::getType)
            .subscribe(requestEventTypes::add);

        Flowable.fromPublisher(responseEvents)
            .map(ReactiveResponse.Event::getType)
            .subscribe(responseEventTypes::add);

        Single<ReactiveResponse> response = Single.fromPublisher(request.response());
        int actualStatus = response.blockingGet()
            .getStatus();

        Assert.assertEquals(6, requestEventTypes.size());
        Assert.assertEquals(5, responseEventTypes.size());

        Assert.assertEquals(actualStatus, HttpStatus.OK_200);
    }

    @AfterClass
    public static void dispose() throws Exception {
        if (httpClient != null) {
            httpClient.stop();
        }
        if (server != null) {
            server.stop();
        }
    }

}
