package com.baeldung.architecture.hexagonal.server.rest.controller;

import com.baeldung.architecture.hexagonal.common.annotation.MethodLogger;
import com.baeldung.architecture.hexagonal.domain.message.model.Message;
import com.baeldung.architecture.hexagonal.server.rest.common.BaseUrl;
import com.baeldung.architecture.hexagonal.server.rest.common.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Version.V1 + BaseUrl.BASE_URL_Message)
public interface IMessageRestController {

    @GetMapping
    @MethodLogger
    List<Message> getAll();

    @PostMapping
    @MethodLogger
    Message create(@RequestBody final Message dto);
}
