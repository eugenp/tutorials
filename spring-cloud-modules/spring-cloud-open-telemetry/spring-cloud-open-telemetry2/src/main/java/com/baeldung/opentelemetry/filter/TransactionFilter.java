//package com.baeldung.opentelemetry.filter;
//
//import com.baeldung.opentelemetry.utils.Utils;
//import io.opentelemetry.api.OpenTelemetry;
//import io.opentelemetry.api.trace.Span;
//import io.opentelemetry.api.trace.SpanKind;
//import io.opentelemetry.api.trace.Tracer;
//import io.opentelemetry.context.Context;
//import io.opentelemetry.context.Scope;
//import io.opentelemetry.context.propagation.TextMapGetter;
//import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.Collections;
//
///**
// * @author : Nam Thang
// * @since : 04/05/2023, Thu
// **/
//@Component
//public class TransactionFilter implements Filter {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionFilter.class);
//
//    private final OpenTelemetry openTelemetry;
//
//    private final Tracer tracer;
//
//    TextMapGetter<HttpServletRequest> getter = new TextMapGetter<>() {
//        @Override
//        public Iterable<String> keys(HttpServletRequest httpServletRequest) {
//            Iterable<String> headerNames = Collections.list(httpServletRequest.getHeaderNames());
//            LOGGER.info("headerNames {}", headerNames);
//            return Collections.list(httpServletRequest.getHeaderNames());
//        }
//
//        @Override
//        public String get(HttpServletRequest httpServletRequest, String s) {
//            LOGGER.info("header request {}", httpServletRequest.getHeader(s));
//            return httpServletRequest.getHeader(s);
//        }
//    };
//
//    @Autowired
//    public TransactionFilter(OpenTelemetry openTelemetry, Tracer tracer) {
//        this.tracer = tracer;
//        this.openTelemetry = openTelemetry;
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        Context extractedContext = openTelemetry.getPropagators().getTextMapPropagator()
//                .extract(Context.current(), req, getter);
//
//        try (Scope scope = extractedContext.makeCurrent()) {
//            // Automatically use the extracted SpanContext as parent.
//            Span serverSpan = tracer.spanBuilder("doFilter")
//                    .setSpanKind(SpanKind.SERVER)
//                    .startSpan();
//
//            try {
//                serverSpan.setAttribute(SemanticAttributes.HTTP_METHOD, req.getMethod());
//                serverSpan.setAttribute(SemanticAttributes.HTTP_HOST, req.getServerName());
//                serverSpan.setAttribute(SemanticAttributes.HTTP_SCHEME, req.getScheme());
//                serverSpan.setAttribute(SemanticAttributes.HTTP_TARGET, req.getRequestURI());
//                serverSpan.setAttribute(SemanticAttributes.HTTP_FLAVOR, req.getProtocol());
//                serverSpan.setAttribute(SemanticAttributes.HTTP_CLIENT_IP, req.getRemoteAddr());
////                serverSpan.setAttribute(SemanticAttributes.HTTP_SERVER_NAME, req.getServerName());
//                serverSpan.setAttribute(SemanticAttributes.HTTP_USER_AGENT, req.getHeader("User-Agent"));
//                serverSpan.setAttribute(SemanticAttributes.HTTP_REQUEST_CONTENT_LENGTH, req.getContentLength());
////                serverSpan.setAttribute(SemanticAttributes.HTTP_REQUEST_CONTENT_LENGTH_UNCOMPRESSED, req.getContentLength());
//
//                filterChain.doFilter(servletRequest, servletResponse);
//            } finally {
//                serverSpan.end();
//            }
//        }
//    }
//}
