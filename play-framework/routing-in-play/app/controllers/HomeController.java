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

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result writer(String author) {
        return ok("Routing in Play by " + author);
    }

    public Result viewUser(String userId) {
        final String response = String.format("Got user id {} in request.", userId);
        return ok(response);
    }

    public Result greet(String name, int age) {
        return ok("Hello " + name + ", you are " + age + " years old");
    }

    public Result squareMe(Long num) {
        return ok(num + " Squared is " + (num * num));
    }

    public Result writer(String author, int id) {
        return ok("Routing in Play by: " + author + " ID: " + id);
    }

    public Result introduceMe(String data) {
        String[] clientData = data.split(",");
        return ok("Your name is " + clientData[0] + ", you are " + clientData[1] + " years old");
    }
}
