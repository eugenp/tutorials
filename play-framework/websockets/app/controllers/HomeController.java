package controllers;

import actors.Messenger;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.fasterxml.jackson.databind.JsonNode;
import dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import play.libs.ws.*;
import play.libs.F;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import utils.MessageConverter;

import javax.inject.Inject;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
@Slf4j
public class HomeController extends Controller implements WSBodyReadables, WSBodyWritables {
    private final ActorSystem actorSystem;
    private final Materializer materializer;
    private final WSClient ws;

    @Inject
    public HomeController(ActorSystem actorSystem, Materializer materializer, WSClient ws) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        this.ws = ws;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) {
//        String url = routes.HomeController.socket().webSocketURL(request);
        String url = routes.HomeController.akkaStreamsSocket().webSocketURL(request);
        return ok(views.html.index.render(url));
    }


    public WebSocket socket() {
        return WebSocket.Json.acceptOrResult(request -> {
            return CompletableFuture.completedFuture(F.Either.Right(
                    ActorFlow.actorRef(out -> Messenger.props(out), actorSystem, materializer)));
        });

    }

    public WebSocket akkaStreamsSocket() {
        return WebSocket.Text.accept(
                request -> {
                    // Log events to the console
                    Sink<String, ?> in = Sink.foreach(System.out::println);

                    // Send a single 'Hello!' message and then leave the socket open
//                    Source<String, ?> out = Source.single("Hello!").concat(Source.maybe());
                    Source<String, ?> another = Source.tick(Duration.ofSeconds(2), Duration.ofSeconds(2), getRandomString());

                    return Flow.fromSinkAndSource(in, another);
                });
    }

    private String getRandomString() {
        log.info("Random string method called.");
        final int postId = ThreadLocalRandom.current().nextInt(0, 100);
        WSRequest request = ws.url("https://jsonplaceholder.typicode.com/posts/" + postId);
        WSRequest timedRequest = request.setRequestTimeout(Duration.of(1000, ChronoUnit.MILLIS));
        CompletionStage<? extends WSResponse> responsePromise = timedRequest.get();

        CompletionStage<MessageDTO> jsonPromise = ws.url("https://jsonplaceholder.typicode.com/posts/" + postId)
                .get()
                .thenApply(r -> r.getBody(json()))
                .thenApply(jsonNode -> MessageConverter.jsonNodeToMessage(jsonNode));
        try {
            return jsonPromise.toCompletableFuture().get().getBody();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "No result";
    }

}
