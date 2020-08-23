package com.baeldung.asyncvsflux;

import java.util.concurrent.CompletableFuture;
import javax.servlet.http.HttpServletRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {

    @GetMapping("/async_result")
    @Async
    public CompletableFuture<String> getResultAsyc(HttpServletRequest request) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("Result is ready!");
    }

}
