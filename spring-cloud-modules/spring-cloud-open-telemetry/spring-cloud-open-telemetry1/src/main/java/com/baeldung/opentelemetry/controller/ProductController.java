package com.baeldung.opentelemetry.controller;

import com.baeldung.opentelemetry.api.client.PriceClient;
import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.model.Product;
import com.baeldung.opentelemetry.repository.ProductRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final PriceClient priceClient;

    private final ProductRepository productRepository;

    private final MeterRegistry registry;

    private final Tracer tracer;

    private final RestTemplate restTemplate;

    @Value("${priceClient.baseUrl}")
    private String baseUrl;

    @Autowired
    public ProductController(PriceClient priceClient, ProductRepository productRepository, MeterRegistry registry, Tracer tracer, RestTemplate restTemplate) {
        this.priceClient = priceClient;
        this.productRepository = productRepository;
        this.registry = registry;
        this.tracer = tracer;
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/product/{id}")
    public Product getProductDetails(@PathVariable("id") long productId){
        LOGGER.info("Getting Product and Price Details With Product Id {}", productId);
        LOGGER.info("Start my wonderful use case");
        Span span = tracer.spanBuilder("Start my wonderful use case").startSpan();
        try {
            Product product = productRepository.getProduct(productId);

            Span childSpan = tracer.spanBuilder("child-1-asda")
                    .setSpanKind(SpanKind.CLIENT)
                    .startSpan();
            try (Scope scope = childSpan.makeCurrent()) {

                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                HttpEntity<String> entity = new HttpEntity<>("Ugurcan", headers);
                String url = String.format("%s/price/%d", baseUrl, productId);
                ResponseEntity<Price> price = restTemplate.exchange(url, HttpMethod.GET, entity, Price.class);
                product.setPrice(price.getBody());

            } catch (Exception e) {
                System.err.println(e);
                childSpan.setStatus(StatusCode.ERROR, "Something bad happened!");
                Attributes eventAttributes2 = Attributes.of(AttributeKey.stringKey("error"), e.toString());
                childSpan.addEvent("ERROR: ", eventAttributes2);
            } finally {
                childSpan.end();
            }
            return product;

        } catch (Exception ex) {
            span.end();
            return null;
        }
    }
}
