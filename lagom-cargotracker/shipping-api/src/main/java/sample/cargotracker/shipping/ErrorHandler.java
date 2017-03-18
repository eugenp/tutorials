package sample.cargotracker.shipping;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Overrides the default HTTP error handler to return JSON
 */
public class ErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public ErrorHandler(Configuration configuration, Environment environment, OptionalSourceMapper sourceMapper, Provider<Router> routes) {
        super(configuration, environment, sourceMapper, routes);
    }

    /**
     * Invoked in dev mode when a server error occurs.
     *
     * @param request The request that triggered the error.
     * @param exception The exception.
     */
    protected CompletionStage<Result> onDevServerError(Http.RequestHeader request, UsefulException exception) {

        ObjectNode jsonError = Json.newObject();

        final Throwable cause = exception.cause;
        final String description = exception.description;
        final String id = exception.id;
        final String title = exception.title;

        jsonError.put("description", description);
        jsonError.put("title", title);
        jsonError.put("id", id);
        jsonError.put("message", exception.getMessage());
        jsonError.set("cause", causesToJson(cause));

        return CompletableFuture.completedFuture(Results.internalServerError(jsonError));
    }

    private ArrayNode causesToJson(Throwable throwable) {
        ArrayNode causesNode = JsonNodeFactory.instance.arrayNode();

        while (throwable != null) {
            ObjectNode causeNode = causesNode.addObject();
            causeNode.put("message", throwable.getMessage());
            causeNode.put("type", throwable.getClass().getName());

            throwable = throwable.getCause();
        }

        return causesNode;
    }
}
