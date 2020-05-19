package controllers;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import play.twirl.api.Html;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private HttpExecutionContext ec;

    @Inject
    public HomeController(HttpExecutionContext ec) {
        this.ec = ec;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result applyHtml() {
        return ok(Html.apply("<h1>This text will appear as a heading 1</h1>"));
    }

    public Result badRequestPage() {
        return badRequest("Your request data has issues.");
    }

    public Result notFoundPage() {
        return notFound("Could not find the page you requested.");
    }

    public Result customContentType() {
        return ok("This is some text content").as("text/html");
    }

    public CompletionStage<Result> asyncOperation() {
        return supplyAsync(() -> {
            return longRunningTask();
        }, ec.current())
                .thenApplyAsync(s -> {
                    return ok("Got result -> " + s);
                }, ec.current());
    }

    private String longRunningTask() {
        return "Long running task has completed";
    }

    public Result setHeaders() {
        return ok("This is some text content")
                .as("text/html")
                .withHeader("Header-Key", "Some value");
    }
}
