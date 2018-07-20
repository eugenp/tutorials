package com.baeldung.web;

import com.baeldung.Constants;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class SseEmitterController {

    @GetMapping(Constants.API_SSE)
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();

        ExecutorService nonBlockingService = Executors.newSingleThreadExecutor();
        nonBlockingService.execute(() -> {
            try {
                emitter.send(Constants.API_SSE_MSG + " @ " + new Date());
                emitter.complete();
            } catch (Exception ex) {
                System.out.println(Constants.GENERIC_EXCEPTION);
                emitter.completeWithError(ex);
            }
        });

        return emitter;
    }

}