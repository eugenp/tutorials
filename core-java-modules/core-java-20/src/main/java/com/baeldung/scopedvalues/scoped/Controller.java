package com.baeldung.scopedvalues.scoped;

import com.baeldung.scopedvalues.scoped.Service;
import com.baeldung.scopedvalues.data.Data;
import com.baeldung.scopedvalues.data.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class Controller {

    private final Service service = new Service();

    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        Optional<Data> data = service.getData(request);
        if (data.isPresent()) {
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response.setContentType("application/json");
            out.print(data.get());
            out.flush();
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
    }

}
