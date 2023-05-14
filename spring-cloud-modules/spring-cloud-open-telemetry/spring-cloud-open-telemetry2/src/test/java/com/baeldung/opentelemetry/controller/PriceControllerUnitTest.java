package com.baeldung.opentelemetry.controller;


import com.baeldung.opentelemetry.exception.PriceNotFoundException;
import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.repository.PriceRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PriceController.class)
class PriceControllerUnitTest {

    @MockBean
    private PriceRepository priceRepository;

    @MockBean
    private MeterRegistry registry;

    @MockBean
    private Tracer tracer;

    @MockBean
    private Span span;

    @MockBean
    private SpanBuilder spanBuilder;

    @MockBean
    private OpenTelemetry openTelemetry;

    @MockBean
    private ContextPropagators contextPropagators;

    @MockBean
    private TextMapPropagator textMapPropagator;

    @MockBean
    private Context context;

    @MockBean
    private Counter counter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenProductandPriceAvailable_whenGetProductCalled_thenReturnProductDetails() throws Exception {
        long productId = 100000L;
        Price price = new Price();
        price.setProductId(productId);
        price.setPriceAmount(12.00);
        price.setDiscount(2.5);

        when(tracer.spanBuilder(any())).thenReturn(spanBuilder);
        when(spanBuilder.setParent(any())).thenReturn(spanBuilder);
        when(spanBuilder.setSpanKind(any())).thenReturn(spanBuilder);
        when(spanBuilder.startSpan()).thenReturn(span);
        when(openTelemetry.getPropagators()).thenReturn(contextPropagators);
        when(contextPropagators.getTextMapPropagator()).thenReturn(textMapPropagator);
        when(textMapPropagator.extract(any(), any(), any())).thenReturn(context);
        when(registry.counter(anyString(), anyString(), anyString())).thenReturn(counter);

        doNothing().when(counter).increment();
        doNothing().when(span).end();

        when(priceRepository.getPrice(productId)).thenReturn(price);

        mockMvc.perform(get("/price/" + productId))
          .andExpect(status().is(HttpStatus.OK.value()));
    }


    @Test
    void givenProductNotFound_whenGetProductCalled_thenReturnInternalServerError() throws Exception {
        long productId = 100000L;

        when(tracer.spanBuilder(any())).thenReturn(spanBuilder);
        when(spanBuilder.setParent(any())).thenReturn(spanBuilder);
        when(spanBuilder.setSpanKind(any())).thenReturn(spanBuilder);
        when(spanBuilder.startSpan()).thenReturn(span);
        when(openTelemetry.getPropagators()).thenReturn(contextPropagators);
        when(contextPropagators.getTextMapPropagator()).thenReturn(textMapPropagator);
        when(textMapPropagator.extract(any(), any(), any())).thenReturn(context);
        when(registry.counter(anyString(), anyString(), anyString())).thenReturn(counter);

        doNothing().when(counter).increment();
        doNothing().when(span).end();
        when(priceRepository.getPrice(productId)).thenThrow(PriceNotFoundException.class);

        mockMvc.perform(get("/price/" + productId))
          .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}
