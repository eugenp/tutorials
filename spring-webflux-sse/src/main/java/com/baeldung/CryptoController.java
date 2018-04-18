package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/crypto/transaction")
public class CryptoController {

    @Autowired WebClientDemoApplication.CryptoService cryptoService;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE) public Flux<Crypto> cryptoEvents() {
        return cryptoService.getCryptos();
    }
}