package com.baeldung.reactive.client;

import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.baeldung.reactive.model.CpuUsage;

import reactor.core.publisher.Flux;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.assertj.core.util.Lists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class CpuUsageClientUnitTest {

    WebClient mockWebClient;

    @SuppressWarnings("rawtypes")
    RequestHeadersUriSpec mockRequestHeadersUriSpec;

    ResponseSpec mockResponseSpec;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Before
    public void setup() {
        mockWebClient = mock(WebClient.class);
        mockRequestHeadersUriSpec = mock(RequestHeadersUriSpec.class);
        RequestHeadersSpec mockRequestHeadersSpec = mock(RequestHeadersSpec.class);
        mockResponseSpec = mock(ResponseSpec.class);

        when(mockWebClient.get()).thenReturn(mockRequestHeadersUriSpec);
        when(mockRequestHeadersUriSpec.uri(anyString())).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
    }
    
    private void mockWebClientResponse(Flux<CpuUsage> mockFluxResponse) {
        when(mockResponseSpec.bodyToFlux(CpuUsage.class)).thenReturn(mockFluxResponse);
    }

    @Test
    public void whenCalled_ObtainsFluxAndPrintsResults() {
        CpuUsage cpuUsage1 = new CpuUsage(30, "Normal");
        CpuUsage cpuUsage2 = new CpuUsage(60, "Warning");
        CpuUsage cpuUsage3 = new CpuUsage(95, "Critical");

        Flux<CpuUsage> cpuUsages = Flux.fromStream(Lists.newArrayList(cpuUsage1, cpuUsage2, cpuUsage3)
            .stream());

        mockWebClientResponse(cpuUsages);

        CpuUsageClient cpuUsageClient = new CpuUsageClient();
        cpuUsageClient.setWebClient(mockWebClient);

        cpuUsageClient.runClient();

        String output = outputCapture.toString();

        verify(mockRequestHeadersUriSpec, times(1)).uri("http://localhost:8080/cpu-usage");
        assertThat(output).contains(cpuUsage1.toString());
        assertThat(output).contains(cpuUsage2.toString());
        assertThat(output).contains(cpuUsage3.toString());
    }

}