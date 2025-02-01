package com.baeldung.armeria;

import com.linecorp.armeria.common.*;
import com.linecorp.armeria.common.annotation.Nullable;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.*;
import com.linecorp.armeria.server.logging.AccessLogWriter;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class AnnotatedServer {
    public static void main(String[] args) {
        ServerBuilder sb = Server.builder();
        sb.http(8080);

        sb.tlsSelfSigned();
        sb.https(8443);

        sb.accessLogWriter(AccessLogWriter.common(), true);
        sb.annotatedService(new AnnotatedHandler());

        Server server = sb.build();
        CompletableFuture<Void> future = server.start();
        future.join();
    }

    static class AnnotatedHandler {
        @Get("/handler")
        public String handler() {
            return "Hello, World!";
        }

        @Get("/handler2")
        public String handler2(ServiceRequestContext ctx) {
            return "Hello, " + ctx.path();
        }

        @Get("/handler3/{name}")
        public String handler3(@Param("name") String name) {
            return "Hello, " + name;
        }

        @Post("/byte-body")
        public String byteBody(byte[] body) {
            return "Length: " + body.length;
        }

        @Post("/string-body")
        public String stringBody(String body) {
            return "Hello: " + body;
        }

        @Post("/uppercase-body")
        @RequestConverter(UppercasingRequestConverter.class)
        public String uppercaseBody(String body) {
            return "Hello: " + body;
        }

        @Post("/json-body")
        public String jsonBody(JsonBody body) {
            return body.name + " = " + body.score;
        }

        @Get("/json-response")
        @ProducesJson
        public JsonBody jsonResponse() {
            return new JsonBody("Baeldung", 42);
        }

        @Post("/uppercase-response")
        @ResponseConverter(UppercasingResponseConverter.class)
        public String uppercaseResponse(String body) {
            return "Hello: " + body;
        }

        @Get("/exception")
        @ExceptionHandler(ConflictExceptionHandler.class)
        public String exception() {
            throw new IllegalStateException();
        }

        static class JsonBody {
            String name;
            int score;

            public JsonBody() {}

            public JsonBody(String name, int score) {
                this.name = name;
                this.score = score;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }
        }

        static class UppercasingRequestConverter implements RequestConverterFunction {
            @Override
            public Object convertRequest(ServiceRequestContext ctx, AggregatedHttpRequest request,
                Class<?> expectedResultType, ParameterizedType expectedParameterizedResultType)
                throws Exception {

                if (expectedResultType.isAssignableFrom(String.class)) {
                    return request.content(StandardCharsets.UTF_8).toUpperCase();
                }

                return RequestConverterFunction.fallthrough();
            }
        }

        static class UppercasingResponseConverter implements ResponseConverterFunction {
            @Override
            public HttpResponse convertResponse(ServiceRequestContext ctx, ResponseHeaders headers,
                @Nullable Object result, HttpHeaders trailers) {
                if (result instanceof String) {
                    return HttpResponse.of(HttpStatus.OK, MediaType.PLAIN_TEXT_UTF_8,
                        ((String) result).toUpperCase(), trailers);
                }

                return ResponseConverterFunction.fallthrough();
            }
        }

        static class ConflictExceptionHandler implements ExceptionHandlerFunction {
            @Override
            public HttpResponse handleException(ServiceRequestContext ctx, HttpRequest req, Throwable cause) {
                if (cause instanceof IllegalStateException) {
                    return HttpResponse.of(HttpStatus.CONFLICT);
                }

                return ExceptionHandlerFunction.fallthrough();
            }
        }
    }
}
