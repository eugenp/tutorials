package com.baeldung.opentelemetry.controller;

import com.baeldung.opentelemetry.api.client.PriceClient;
import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.model.Product;
import com.baeldung.opentelemetry.repository.ProductRepository;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final PriceClient priceClient;

    private final ProductRepository productRepository;

    private final Tracer tracer;

    @Autowired
    public ProductController(PriceClient priceClient, ProductRepository productRepository, Tracer tracer) {
        this.priceClient = priceClient;
        this.productRepository = productRepository;
        this.tracer = tracer;
    }

    @GetMapping(path = "/product/{id}")
    public Product getProductDetails(@PathVariable("id") long productId) {
        LOGGER.info("Getting Product and Price Details With Product Id {}", productId);
        LOGGER.info("Start my wonderful use case");

        Span parent = tracer.spanBuilder("Start my wonderful use case").startSpan();
        try {
            LOGGER.info("Processing id {}", productId);
            return doHandle(productId, parent);
        } catch (Exception ex) {
            parent.end();
            return null;
        }
    }

    private Product doHandle(long productId, Span parent) {
        Span parentSpan = tracer.spanBuilder("doHandle").setParent(Context.current().with(parent)).startSpan();
//        Span parentSpan = tracer.spanBuilder("doHandle").setSpanKind(SpanKind.CLIENT).startSpan();
        try (Scope scope = parentSpan.makeCurrent()) {
            Product product = getProduct(productId);
            Price price = getPrice(productId);
            product.setPrice(price);
            return product;
        } finally {
            parentSpan.end();
        }
    }

    private Price getPrice(long productId) {
        Span childSpan = tracer.spanBuilder("/getPrice").setSpanKind(SpanKind.CLIENT).startSpan();
        try (Scope scope = childSpan.makeCurrent()) {

            childSpan.setAttribute(SemanticAttributes.HTTP_METHOD, "GET");

            return priceClient.getPrice(productId);
        } catch (Exception e) {
            LOGGER.error("error: ", e);
            childSpan.setStatus(StatusCode.ERROR, "Something bad happened!");
            Attributes eventAttributes2 = Attributes.of(AttributeKey.stringKey("error"), e.toString());
            childSpan.addEvent("ERROR: ", eventAttributes2);
            return null;
        } finally {
            childSpan.end();
        }
    }

    private Product getProduct(long productId) {
        Span childSpan = tracer.spanBuilder("getProduct").setSpanKind(SpanKind.CLIENT).startSpan();

        try {
            return productRepository.getProduct(productId);
        } catch (Exception e) {
            childSpan.setStatus(StatusCode.ERROR, "Something bad happened!");
            Attributes eventAttributes2 = Attributes.of(AttributeKey.stringKey("error"), e.toString());
            childSpan.addEvent("ERROR: ", eventAttributes2);
            return null;
        } finally {
            childSpan.end();
        }
    }
}
