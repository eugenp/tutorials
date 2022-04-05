package com.baeldung.blade.sample;

import com.baeldung.blade.sample.configuration.BaeldungException;
import com.blade.mvc.WebContext;
import com.blade.mvc.annotation.DeleteRoute;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PostRoute;
import com.blade.mvc.annotation.PutRoute;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;

@Path
public class RouteExampleController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RouteExampleController.class);

    @GetRoute("/route-example")
    public String get() {
        return "get.html";
    }

    @PostRoute("/route-example")
    public String post() {
        return "post.html";
    }

    @PutRoute("/route-example")
    public String put() {
        return "put.html";
    }

    @DeleteRoute("/route-example")
    public String delete() {
        return "delete.html";
    }

    @Route(value = "/another-route-example", method = HttpMethod.GET)
    public String anotherGet() {
        return "get.html";
    }

    @Route(value = "/allmatch-route-example")
    public String allmatch() {
        return "allmatch.html";
    }

    @Route(value = "/triggerInternalServerError")
    public void triggerInternalServerError() {
        int x = 1 / 0;
    }

    @Route(value = "/triggerBaeldungException")
    public void triggerBaeldungException() throws BaeldungException {
        throw new BaeldungException("Foobar Exception to threat differently");
    }

    @Route(value = "/user/foo")
    public void urlCoveredByNarrowedWebhook(Response response) {
        response.text("Check out for the WebHook covering '/user/*' in the logs");
    }

    @GetRoute("/load-configuration-in-a-route")
    public void loadConfigurationInARoute(Response response) {
        String authors = WebContext.blade()
            .env("app.authors", "Unknown authors");
        log.info("[/load-configuration-in-a-route] Loading 'app.authors' from configuration, value: " + authors);
        response.render("index.html");
    }

    @GetRoute("/template-output-test")
    public void templateOutputTest(Request request, Response response) {
        request.attribute("name", "Blade");
        response.render("template-output-test.html");
    }
}
