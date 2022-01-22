package com.baeldung.onceperrequestfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class HelloController implements AutoCloseable {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping(path = "/greeting")
    public DeferredResult<String> hello(HttpServletResponse response) throws Exception {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        executorService.submit(() -> perform(deferredResult));
        return deferredResult;
    }

    private void perform(DeferredResult<String> dr) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dr.setResult("OK");
    }

    @Override
    public void close() throws Exception {
        executorService.shutdownNow();
    }
}
