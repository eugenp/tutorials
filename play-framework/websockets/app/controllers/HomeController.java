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
import play.libs.F;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import utils.MessageConverter;

import javax.inject.Inject;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class HomeController extends Controller {
    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public HomeController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public Result index(Http.Request request) {
        String url = routes.HomeController.socket().webSocketURL(request);
//        String url = routes.HomeController.akkaStreamsSocket().webSocketURL(request);
        return ok(views.html.index.render(url));
    }


    public WebSocket socket() {
        return WebSocket.Json.acceptOrResult(request -> {
            return CompletableFuture.completedFuture(F.Either.Right(
                    ActorFlow.actorRef(out -> Messenger.props(out), actorSystem, materializer)));
        });

    }

    public WebSocket akkaStreamsSocket() {
        return WebSocket.Json.accept(
                request -> {
                    // Log events to the console
                    Sink<JsonNode, ?> in = Sink.foreach(System.out::println);

                    // Send a single 'Hello!' message and then leave the socket open
                    final MessageDTO messageDTO = new MessageDTO("userid", "id", "test title", "test body");
                    Source<JsonNode, ?> out = Source.tick(Duration.ofSeconds(2), Duration.ofSeconds(2), MessageConverter.messageToJsonNode(messageDTO));

                    return Flow.fromSinkAndSource(in, out);
                });
    }
}
