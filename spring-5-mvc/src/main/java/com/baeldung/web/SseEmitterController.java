package com.baeldung.web;

import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.baeldung.Constants;

@Controller
public class SseEmitterController {
    private ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    @GetMapping(Constants.API_SSE)
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();

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

    @GetMapping("/stream-sse-mvc")
    public SseEmitter streamSseMvc() {
        SseEmitter emitter = new SseEmitter();
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();

        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; true; i++) {
                    SseEventBuilder event = SseEmitter.event()
                        .data("SSE MVC - " + LocalTime.now()
                            .toString())
                        .id(String.valueOf(i))
                        .name("sse event - mvc");
                    emitter.send(event);
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }

}
