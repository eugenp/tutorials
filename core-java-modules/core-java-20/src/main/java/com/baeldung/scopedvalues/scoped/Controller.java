package com.baeldung.scopedvalues.scoped;

import com.baeldung.scopedvalues.data.Data;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.incubator.concurrent.ScopedValue;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class Controller {

    private final Service internalService = new Service();

    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        Optional<Data> data = internalService.getData(request);

        ScopedValue.where(Server.LOGGED_IN_USER, null)
                .run(internalService::extractData);

        if (data.isPresent()) {
            try {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                out.print(data.get());
                out.flush();
                response.setStatus(200);
            } catch (IOException e) {
                response.setStatus(500);
            }
        } else {
            response.setStatus(400);
        }
    }

}
