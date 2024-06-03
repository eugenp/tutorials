package com.gateway.web.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ModifyServersOpenApiFilter implements GlobalFilter, Ordered {

    private static final String OPEN_API_PATH = "/v3/api-docs";
    private static final Logger log = LoggerFactory.getLogger(ModifyServersOpenApiFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/services") && path.contains(OPEN_API_PATH)) {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            ServerHttpResponseDecorator decoratedResponse = createModifyServersOpenApiInterceptor(path, originalResponse, bufferFactory);

            // replace response with decorator
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        } else {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public ModifyServersOpenApiInterceptor createModifyServersOpenApiInterceptor(
        String path,
        ServerHttpResponse originalResponse,
        DataBufferFactory bufferFactory
    ) {
        return new ModifyServersOpenApiInterceptor(path, originalResponse, bufferFactory);
    }

    public class ModifyServersOpenApiInterceptor extends ServerHttpResponseDecorator {

        private final String path;
        private final ServerHttpResponse originalResponse;
        private final DataBufferFactory bufferFactory;
        private String rewritedBody = "";

        private ModifyServersOpenApiInterceptor(String path, ServerHttpResponse originalResponse, DataBufferFactory bufferFactory) {
            super(originalResponse);
            this.path = path;
            this.originalResponse = originalResponse;
            this.bufferFactory = bufferFactory;
        }

        public String getRewritedBody() {
            return rewritedBody;
        }

        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            rewritedBody = "";
            if (body instanceof Flux) {
                Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

                return super.writeWith(fluxBody.buffer().map(dataBuffers -> rewriteBodyWithServers(dataBuffers)));
            }
            // when body is not a flux
            return super.writeWith(body);
        }

        private DataBuffer rewriteBodyWithServers(List<? extends DataBuffer> dataBuffers) {
            DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
            DataBuffer join = dataBufferFactory.join(dataBuffers);
            byte[] content = new byte[join.readableByteCount()];
            join.read(content);

            // release memory
            DataBufferUtils.release(join);
            String strBody = contentToString(content);

            try {
                // create custom server
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonBody = mapper.readTree(strBody);
                ObjectNode serversToJson = mapper.createObjectNode();
                serversToJson.set("url", mapper.valueToTree(path.replaceFirst(OPEN_API_PATH + "(/.*)?$", "")));
                serversToJson.set("description", mapper.valueToTree("added by global filter"));

                // add custom server
                ArrayNode servers = mapper.createArrayNode();
                servers.add(serversToJson);
                ((ObjectNode) jsonBody).set("servers", servers);

                rewritedBody = jsonBody.toString();
                return rewritedBodyToDataBuffer();
            } catch (JsonProcessingException e) {
                log.error("Error when modify servers from api-doc of {}: {}", path, e.getMessage());
            }
            return join;
        }

        private DataBuffer rewritedBodyToDataBuffer() {
            if (isZippedResponse()) {
                byte[] zippedBody = zipContent(rewritedBody);
                originalResponse.getHeaders().setContentLength(zippedBody.length);
                return bufferFactory.wrap(zippedBody);
            }
            originalResponse.getHeaders().setContentLength(rewritedBody.getBytes().length);
            return bufferFactory.wrap(rewritedBody.getBytes());
        }

        private String contentToString(byte[] content) {
            if (isZippedResponse()) {
                byte[] unzippedContent = unzipContent(content);
                return new String(unzippedContent, StandardCharsets.UTF_8);
            }
            return new String(content, StandardCharsets.UTF_8);
        }

        private boolean isZippedResponse() {
            return (
                !originalResponse.getHeaders().isEmpty() &&
                originalResponse.getHeaders().get(HttpHeaders.CONTENT_ENCODING) != null &&
                Objects.requireNonNull(originalResponse.getHeaders().get(HttpHeaders.CONTENT_ENCODING)).contains("gzip")
            );
        }

        private byte[] unzipContent(byte[] content) {
            try {
                GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(content));
                byte[] unzippedContent = gzipInputStream.readAllBytes();
                gzipInputStream.close();
                return unzippedContent;
            } catch (IOException e) {
                log.error("Error when unzip content during modify servers from api-doc of {}: {}", path, e.getMessage());
            }
            return content;
        }

        private byte[] zipContent(String content) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(content.length());
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                gzipOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
                gzipOutputStream.flush();
                gzipOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                log.error("Error when zip content during modify servers from api-doc of {}: {}", path, e.getMessage());
            }
            return content.getBytes();
        }
    }
}
