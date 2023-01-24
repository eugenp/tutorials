package com.baeldung.blade.sample;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.http.Session;

@Path
public class AttributesExampleController {

    public final static String REQUEST_VALUE = "Some Request value";
    public final static String SESSION_VALUE = "1337";
    public final static String HEADER = "Some Header";

    @GetRoute("/request-attribute-example")
    public void getRequestAttribute(Request request, Response response) {
        request.attribute("request-val", REQUEST_VALUE);
        String requestVal = request.attribute("request-val");
        response.text(requestVal);
    }

    @GetRoute("/session-attribute-example")
    public void getSessionAttribute(Request request, Response response) {
        Session session = request.session();
        session.attribute("session-val", SESSION_VALUE);
        String sessionVal = session.attribute("session-val");
        response.text(sessionVal);
    }

    @GetRoute("/header-example")
    public void getHeader(Request request, Response response) {
        String headerVal = request.header("a-header", HEADER);
        response.header("a-header", headerVal);
    }

}
