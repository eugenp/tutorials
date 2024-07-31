package com.baeldung.scopedvalues.scoped.inheriting;

import com.baeldung.scopedvalues.data.Data;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.incubator.concurrent.StructuredTaskScope;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Controller {

    private final InternalService internalService = new InternalService();
    private final ExternalService externalService = new ExternalService();

    public void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<Optional<Data>> internalData = scope.fork(() -> internalService.getData(request));
            Future<String> externalData = scope.fork(externalService::getData);
            try {
                scope.join();
                scope.throwIfFailed();

                Optional<Data> data = internalData.resultNow();
                if (data.isPresent()) {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    out.println(data.get());
                    out.print(externalData.resultNow());
                    out.flush();
                    response.setStatus(200);
                } else {
                    response.setStatus(400);
                }
            } catch (InterruptedException | ExecutionException | IOException e) {
                response.setStatus(500);
            }
        }
    }

}
