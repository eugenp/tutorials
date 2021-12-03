package com.baeldung.architecture.hexagonal.server.rest.controller;

import com.baeldung.architecture.hexagonal.common.annotation.MethodLogger;
import com.baeldung.architecture.hexagonal.server.rest.common.BaseUrl;
import com.baeldung.architecture.hexagonal.server.rest.common.Version;
import com.baeldung.architecture.hexagonal.server.rest.dto.MessageDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Version.V1 + BaseUrl.MESSAGE_BASE_URL)
public interface IMessageRestController {

    @GetMapping
    @MethodLogger
    List<MessageDTO> getAll();

    @PostMapping
    @MethodLogger
    MessageDTO create(@RequestBody final MessageDTO dto);
}
