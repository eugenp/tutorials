package com.baeldung.async.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.baeldung.async.dto.ProcessResult;

@Controller
public class AsyncProcessingController {

    static Logger log = Logger.getAnonymousLogger();

    static final int NO_OF_MESSAGE = 2;

    @Autowired
    ThreadPoolTaskExecutor executor;

    @RequestMapping("/resBodyEmitter")
    public ResponseBodyEmitter resBodyEmitter() throws InterruptedException {
        final ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        processRequestInAnotherThread(emitter);
        return emitter;
    }

    @RequestMapping("/resEntityResBodyEmitter")
    public ResponseEntity<ResponseBodyEmitter> enEesBodyEmitter() {
        final ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        processRequestInAnotherThread(emitter);
        return new ResponseEntity<ResponseBodyEmitter>(emitter, HttpStatus.OK);
    }

    @RequestMapping("/sseEmitter")
    public SseEmitter sseEmitter() throws InterruptedException {
        final SseEmitter emitter = new SseEmitter();
        processRequestInAnotherThread(emitter);
        return emitter;
    }

    @RequestMapping("/resEntitySseEmitter")
    public ResponseEntity<SseEmitter> resEntitySseEmitter() throws InterruptedException {
        final SseEmitter emitter = new SseEmitter();
        processRequestInAnotherThread(emitter);
        return new ResponseEntity<SseEmitter>(emitter, HttpStatus.OK);
    }

    @RequestMapping("/streamingResponseBody")
    public StreamingResponseBody streamingResponseBody() {
        return getSimpleStreamingResponseBody();
    }

    @RequestMapping("/resStreamingResponseBody")
    public ResponseEntity<StreamingResponseBody> resStreamingResponseBody() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("filename", "streamingResponseBody.txt");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.valueOf("application/force-download"))
                .body(getSimpleStreamingResponseBody());
    }

    private void processRequestInAnotherThread(ResponseBodyEmitter emitter) {
        executor.execute(() -> {
            for (int i = 0; i < NO_OF_MESSAGE; ++i) {
                log.info("Send: " + i);
                try {
                    ProcessResult progress = new ProcessResult("OK", i);
                    emitter.send(progress, MediaType.APPLICATION_JSON);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Failed to send", e);
                    emitter.completeWithError(e);
                }
            }
            emitter.complete();
        });
    }

    private StreamingResponseBody getSimpleStreamingResponseBody() {
        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                log.info("Start write steam");
                outputStream.write("StreamingResponseBody".getBytes());
            }
        };
    }

}
