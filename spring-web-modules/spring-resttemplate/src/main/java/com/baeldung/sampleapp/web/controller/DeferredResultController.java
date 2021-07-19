package com.baeldung.sampleapp.web.controller;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class DeferredResultController {

    private final static Logger LOG = LoggerFactory.getLogger(DeferredResultController.class);

    @GetMapping("/async-deferredresult")
    public DeferredResult<ResponseEntity<?>> handleReqDefResult(Model model) {
        LOG.info("Received request");
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();

        deferredResult.onCompletion(() -> LOG.info("Processing complete"));

        CompletableFuture.supplyAsync(() -> {
            LOG.info("Processing in separate thread");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "OK";
        })
            .whenCompleteAsync((result, exc) -> deferredResult.setResult(ResponseEntity.ok(result)));

        LOG.info("Servlet thread freed");
        return deferredResult;
    }

    @GetMapping("/process-blocking")
    public ResponseEntity<?> handleReqSync(Model model) {
        // ...
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/async-deferredresult-timeout")
    public DeferredResult<ResponseEntity<?>> handleReqWithTimeouts(Model model) {
        LOG.info("Received async request with a configured timeout");
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(500l);
        deferredResult.onTimeout(() -> deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
            .body("Request timeout occurred.")));

        CompletableFuture.supplyAsync(() -> {
            LOG.info("Processing in separate thread");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "error";
        })
            .whenCompleteAsync((result, exc) -> deferredResult.setResult(ResponseEntity.ok(result)));
        LOG.info("servlet thread freed");
        return deferredResult;
    }

    @GetMapping("/async-deferredresult-error")
    public DeferredResult<ResponseEntity<?>> handleAsyncFailedRequest(Model model) {
        LOG.info("Received async request with a configured error handler");
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        deferredResult.onError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) {
                deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred."));
            }

        });
        LOG.info("servlet thread freed");
        return deferredResult;
    }

}
