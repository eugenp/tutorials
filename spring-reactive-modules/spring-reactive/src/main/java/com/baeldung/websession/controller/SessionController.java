package com.baeldung.websession.controller;

import com.baeldung.websession.transfer.CustomResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
public class SessionController {

    @GetMapping("/websession/test")
    public Mono<CustomResponse> testWebSessionByParam(
            @RequestParam(value = "id") int id,
            @RequestParam(value = "note") String note,
            WebSession session) {

        session.getAttributes().put("id", id);
        session.getAttributes().put("note", note);

        CustomResponse r = new CustomResponse();
        r.setId((int) session.getAttributes().get("id"));
        r.setNote((String) session.getAttributes().get("note"));

        return Mono.just(r);
    }

    @GetMapping("/websession")
    public Mono<CustomResponse> getSession(WebSession session) {

        session.getAttributes().putIfAbsent("id", 0);
        session.getAttributes().putIfAbsent("note", "Howdy Cosmic Spheroid!");

        CustomResponse r = new CustomResponse();
        r.setId((int) session.getAttributes().get("id"));
        r.setNote((String) session.getAttributes().get("note"));

        return Mono.just(r);
    }

}