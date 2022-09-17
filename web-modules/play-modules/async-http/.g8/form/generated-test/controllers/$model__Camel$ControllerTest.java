package controllers;

import org.junit.Test;
import play.Application;
import play.filters.csrf.*;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.*;
import play.test.WithApplication;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.RequestBuilder;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.api.test.CSRFTokenHelper.*;

public class $model;format="Camel"$ControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void test$model;format="Camel"$Get() {
        RequestBuilder request = new RequestBuilder()
                .method(GET)
                .uri("/$model;format="camel"$");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void test$model;format="Camel"$Post() {
        HashMap<String, String> formData = new HashMap<>();
        formData.put("name", "play");
        formData.put("age", "4");
        RequestBuilder request = addCSRFToken(new RequestBuilder()
                .header(Http.HeaderNames.HOST, "localhost")
                .method(POST)
                .bodyForm(formData)
                .uri("/$model;format="camel"$"));

        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
    }

}
