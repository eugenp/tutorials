package com.baeldung.payment.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.payment.domain.Payment;
import com.baeldung.payment.service.PaymentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PaymentController {

	@Autowired
	private PaymentService service;

	@GetMapping("/paymentinformation/{id}")
	public Flux<Payment> getPaymentInformation(@PathVariable(value = "id") Integer paymentId) {
		return service.getPaymentInformation(paymentId);
	}

	@PostMapping(value = "/makepayment", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Boolean> createTweets(@RequestBody Payment payment) {
		return service.makePayment(payment);
	}

}