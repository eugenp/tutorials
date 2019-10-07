package controllers;

import actors.Messenger;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import play.data.FormFactory;
import play.libs.F;
import play.libs.streams.ActorFlow;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
@Slf4j
public class HomeController extends Controller {
    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public HomeController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) {
        String url = routes.HomeController.socket().webSocketURL(request);
        return ok(views.html.index.render(url));
    }


    public WebSocket socket() {
        return WebSocket.Json.acceptOrResult(request -> {
            return CompletableFuture.completedFuture(
                    F.Either.<Result, Flow<JsonNode, JsonNode, ?>>Right(
                                        ActorFlow.actorRef(
                                                out -> Messenger.props(out), actorSystem, materializer))
            );
        });

    }

}
