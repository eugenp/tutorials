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
import java.util.concurrent.CompletionStage;

@Slf4j
public class HomeController extends Controller {
    private ActorSystem actorSystem;
    private Materializer materializer;

    @Inject
    public HomeController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public Result index(Http.Request request) {
        String url = routes.HomeController.socket().webSocketURL(request);
        //To test WebSockets with akka streams, uncomment the next line and comment out the previous
        //String url = routes.HomeController.akkaStreamsSocket().webSocketURL(request);
        return ok(views.html.index.render(url));
    }


    public WebSocket socket() {
        return WebSocket.Json.acceptOrResult(this::createActorFlow);
    }

    private CompletionStage<F.Either<Result, Flow<JsonNode, JsonNode, ?>>> createActorFlow(
      Http.RequestHeader request) {
        return CompletableFuture.completedFuture(F.Either.Right(createFlowForActor()));
    }

    private CompletionStage<F.Either<Result, Flow<JsonNode, JsonNode, ?>>>
      createActorFlow2(Http.RequestHeader request) {
        return CompletableFuture.completedFuture(
          request.session()
          .getOptional("username")
          .map(username ->
            F.Either.<Result, Flow<JsonNode, JsonNode, ?>>Right(
              createFlowForActor()))
          .orElseGet(() -> F.Either.Left(forbidden())));
    }

    private Flow<JsonNode, JsonNode, ?> createFlowForActor() {
        return ActorFlow.actorRef(out -> Messenger.props(out), actorSystem, materializer);
    }

    public WebSocket akkaStreamsSocket() {
        return WebSocket.Json.accept(
          request -> {
              Sink<JsonNode, ?> in = Sink.foreach(System.out::println);
              MessageDTO messageDTO = new MessageDTO("1", "1", "Title", "Test Body");
              Source<JsonNode, ?> out = Source.tick(
                Duration.ofSeconds(2),
                Duration.ofSeconds(2),
                MessageConverter.messageToJsonNode(messageDTO)
              );
              return Flow.fromSinkAndSource(in, out);
        });
    }
}
