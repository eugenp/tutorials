package com.baeldung.oauth2.client;

import org.eclipse.microprofile.config.Config;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/downstream")
public class DownstreamCallServlet extends HttpServlet {

    @Inject
    private Config config;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(config.getValue("resourceServerUri", String.class));
        WebTarget resourceWebTarget;
        String response = null;
        JsonObject tokenResponse = (JsonObject) request.getSession().getAttribute("tokenResponse");
        if ("read".equals(action)) {
            resourceWebTarget = webTarget.path("resource/read");
            Invocation.Builder invocationBuilder = resourceWebTarget.request();
            response = invocationBuilder
                    .header("authorization", tokenResponse.getString("access_token"))
                    .get(String.class);
        } else if ("write".equals(action)) {
            resourceWebTarget = webTarget.path("resource/write");
            Invocation.Builder invocationBuilder = resourceWebTarget.request();
            response = invocationBuilder
                    .header("authorization", tokenResponse.getString("access_token"))
                    .post(Entity.text("body string"), String.class);
        }
        PrintWriter out = resp.getWriter();
        out.println(response);
        out.flush();
        out.close();
    }
}
