package com.baeldung.cache;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

@Controller
public class CacheControlController {

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> helloWorld(@PathVariable String name) {
        CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS)
                                      .noTransform()
                                      .mustRevalidate();
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body("Hello " + name);
    }

    @RequestMapping(value = "/home/{name}", method = RequestMethod.GET)
    public String home(@PathVariable String name, final HttpServletResponse response) {
        response.addHeader("Cache-Control", "max-age=60, must-revalidate, no-transform");
        return "home";
    }

    @RequestMapping(value = "/cache/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> intercept(@PathVariable String name) {
        return ResponseEntity.ok().body("Hello " + name);
    }

    @RequestMapping(value = "/validate/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> validate(@PathVariable String name, WebRequest request) {

        ZoneId zoneId = ZoneId.of("GMT");
        long lastModifiedTimestamp = LocalDateTime.of(2020, 02, 4, 19, 57, 45)
          .atZone(zoneId).toInstant().toEpochMilli();

        if (request.checkNotModified(lastModifiedTimestamp)) {
            return ResponseEntity.status(304).build();
        }

        return ResponseEntity.ok().body("Hello " + name);
    }


}
