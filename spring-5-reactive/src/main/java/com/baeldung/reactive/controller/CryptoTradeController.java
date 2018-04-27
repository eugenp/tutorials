package com.baeldung.reactive.controller;


import com.baeldung.reactive.model.CryptoCurrency;
import com.baeldung.reactive.util.CryptoGenerator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class CryptoTradeController {


  @GetMapping(value = "/trade",
          produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ResponseBody
  public Flux<CryptoCurrency> getAllCryptoRates() {
    return new CryptoGenerator().
            fetchCryptoStream(Duration.ofMillis(1000));// 1 Second interval
  }




}
