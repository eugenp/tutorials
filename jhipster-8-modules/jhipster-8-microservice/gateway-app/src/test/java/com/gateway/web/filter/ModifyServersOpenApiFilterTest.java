package com.gateway.web.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class ModifyServersOpenApiFilterTest {

    private static final Logger log = LoggerFactory.getLogger(ModifyServersOpenApiFilterTest.class);
    private final GatewayFilterChain filterChain = mock(GatewayFilterChain.class);
    private final ArgumentCaptor<ServerWebExchange> captor = ArgumentCaptor.forClass(ServerWebExchange.class);

    @BeforeEach
    void setup() {
        when(filterChain.filter(captor.capture())).thenReturn(Mono.empty());
    }

    @Test
    void shouldCallCreateModifyServersOpenApiInterceptorWhenGetOpenApiSpec() {
        // dummy api url to filter
        String sample_url = "/services/service-test/instance-test/v3/api-docs";

        // create request
        MockServerHttpRequest request = MockServerHttpRequest.get(sample_url).build();
        ServerWebExchange exchange = MockServerWebExchange.from(request);

        // apply the filter to the request
        ModifyServersOpenApiFilter modifyServersOpenApiFilter = spy(new ModifyServersOpenApiFilter());
        modifyServersOpenApiFilter.filter(exchange, filterChain).subscribe();

        verify(modifyServersOpenApiFilter, times(1)).createModifyServersOpenApiInterceptor(
            sample_url,
            exchange.getResponse(),
            exchange.getResponse().bufferFactory()
        );
    }

    @Test
    void shouldNotCallCreateModifyServersOpenApiInterceptorWhenNotGetOpenApiSpec() {
        // dummy api url to filter
        String sample_url = "/services/service-test/instance-test/api";

        // create request
        MockServerHttpRequest request = MockServerHttpRequest.get(sample_url).build();
        ServerWebExchange exchange = MockServerWebExchange.from(request);

        // apply the filter to the request
        ModifyServersOpenApiFilter modifyServersOpenApiFilter = spy(new ModifyServersOpenApiFilter());
        modifyServersOpenApiFilter.filter(exchange, filterChain).subscribe();

        verify(modifyServersOpenApiFilter, times(0)).createModifyServersOpenApiInterceptor(
            sample_url,
            exchange.getResponse(),
            exchange.getResponse().bufferFactory()
        );
    }

    @Test
    void shouldOrderToMinusOne() {
        ModifyServersOpenApiFilter modifyServersOpenApiFilter = new ModifyServersOpenApiFilter();
        assertEquals(modifyServersOpenApiFilter.getOrder(), -1);
    }

    @Nested
    class ModifyServersOpenApiInterceptorTest {

        private final String path = "/services/service-test/instance-test/v3/api-docs";
        private final MockServerHttpRequest request = MockServerHttpRequest.get(path).build();
        private final ServerWebExchange exchange = MockServerWebExchange.from(request);
        private final ModifyServersOpenApiFilter modifyServersOpenApiFilter = new ModifyServersOpenApiFilter();

        @Test
        void shouldRewriteBodyWhenBodyIsFluxAndResponseIsNotZipped() {
            ModifyServersOpenApiFilter.ModifyServersOpenApiInterceptor interceptor =
                modifyServersOpenApiFilter.createModifyServersOpenApiInterceptor(
                    path,
                    exchange.getResponse(),
                    exchange.getResponse().bufferFactory()
                );

            byte[] bytes = "{}".getBytes();
            DataBuffer body = exchange.getResponse().bufferFactory().wrap(bytes);
            interceptor.writeWith(Flux.just(body)).subscribe();
            assertThat(
                interceptor
                    .getRewritedBody()
                    .contains("\"servers\":[{\"url\":\"/services/service-test/instance-test\",\"description\":\"added by global filter\"}]")
            ).isTrue();
        }

        @Test
        void shouldRewriteBodyWhenBodyIsFluxAndResponseIsZipped() {
            exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_ENCODING, "gzip");
            ModifyServersOpenApiFilter.ModifyServersOpenApiInterceptor interceptor =
                modifyServersOpenApiFilter.createModifyServersOpenApiInterceptor(
                    path,
                    exchange.getResponse(),
                    exchange.getResponse().bufferFactory()
                );

            byte[] bytes = zipContent();
            DataBuffer body = exchange.getResponse().bufferFactory().wrap(bytes);
            interceptor.writeWith(Flux.just(body)).subscribe();
            assertThat(
                interceptor
                    .getRewritedBody()
                    .contains("\"servers\":[{\"url\":\"/services/service-test/instance-test\",\"description\":\"added by global filter\"}]")
            ).isTrue();
        }

        @Test
        void shouldNotRewriteBodyWhenBodyIsNotFlux() {
            ModifyServersOpenApiFilter.ModifyServersOpenApiInterceptor interceptor =
                modifyServersOpenApiFilter.createModifyServersOpenApiInterceptor(
                    path,
                    exchange.getResponse(),
                    exchange.getResponse().bufferFactory()
                );

            byte[] bytes = "{}".getBytes();
            DataBuffer body = exchange.getResponse().bufferFactory().wrap(bytes);
            interceptor.writeWith(Mono.just(body)).subscribe();
            assertThat(interceptor.getRewritedBody()).isEmpty();
        }

        private byte[] zipContent() {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream("{}".length());
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                gzipOutputStream.write("{}".getBytes(StandardCharsets.UTF_8));
                gzipOutputStream.flush();
                gzipOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                log.error("Error in test when zip content during modify servers from api-doc of {}: {}", path, e.getMessage());
            }
            return "{}".getBytes();
        }
    }
}
