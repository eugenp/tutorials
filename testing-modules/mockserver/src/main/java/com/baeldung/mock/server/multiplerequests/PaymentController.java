package com.baeldung.mock.server.multiplerequests;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final WebClient webClient;

    public PaymentController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("payment/process")
    public ResponseEntity<PaymentGatewayResponse> submitPayment(@RequestBody PaymentGatewayRequest paymentGatewayRequest) throws JSONException {

        String paymentSubmissionResponse = webClient.post()
            .uri("http://localhost:9090/payment/submit")
            .body(BodyInserters.fromValue(paymentGatewayRequest))
            .retrieve()
            .bodyToMono(String.class)
            .block();

        UUID paymentId = UUID.fromString(new JSONObject(paymentSubmissionResponse).getString("paymentId"));
        PaymentGatewayResponse.PaymentStatus paymentStatus = PaymentGatewayResponse.PaymentStatus.PENDING;
        while (paymentStatus.equals(PaymentGatewayResponse.PaymentStatus.PENDING)) {
            String paymentStatusResponse = webClient.get()
                .uri("http://localhost:9090/payment/status/%s".formatted(paymentId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
            paymentStatus = PaymentGatewayResponse.PaymentStatus.valueOf(new JSONObject(paymentStatusResponse).getString("paymentStatus"));
            logger.info("Payment Status {}", paymentStatus);
        }
        return new ResponseEntity<>(new PaymentGatewayResponse(paymentId, paymentStatus), HttpStatus.OK);
    }
}
