package com.baeldung.graphql.fileupload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import reactor.core.publisher.Mono;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.http.MediaType;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.Assert;
import org.springframework.util.IdGenerator;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_GRAPHQL;

public class MultipartGraphQlHttpHandler {

    private static final Log logger = LogFactory.getLog(MultipartGraphQlHttpHandler.class);

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_PARAMETERIZED_TYPE_REF = new ParameterizedTypeReference<Map<String, Object>>() {
    };

    private static final ParameterizedTypeReference<Map<String, List<String>>> LIST_PARAMETERIZED_TYPE_REF = new ParameterizedTypeReference<Map<String, List<String>>>() {
    };

    public static final List<MediaType> SUPPORTED_MEDIA_TYPES = Arrays.asList(APPLICATION_GRAPHQL, MediaType.APPLICATION_JSON, MediaType.APPLICATION_GRAPHQL);

    private final IdGenerator idGenerator = new AlternativeJdkIdGenerator();

    private final WebGraphQlHandler graphQlHandler;

    private final GenericHttpMessageConverter genericHttpMessageConverter;

    public MultipartGraphQlHttpHandler(WebGraphQlHandler graphQlHandler, GenericHttpMessageConverter genericHttpMessageConverter) {
        Assert.notNull(graphQlHandler, "WebGraphQlHandler is required");
        Assert.notNull(genericHttpMessageConverter, "GenericHttpMessageConverter is required");
        this.graphQlHandler = graphQlHandler;
        this.genericHttpMessageConverter = genericHttpMessageConverter;
    }

    public ServerResponse handleMultipartRequest(ServerRequest serverRequest) throws ServletException {
        HttpServletRequest httpServletRequest = serverRequest.servletRequest();

        Map<String, Object> inputQuery = Optional.ofNullable(this.<Map<String, Object>>deserializePart(httpServletRequest, "operations", MAP_PARAMETERIZED_TYPE_REF.getType())).orElse(new HashMap<>());

        final Map<String, Object> queryVariables = getFromMapOrEmpty(inputQuery, "variables");
        final Map<String, Object> extensions = getFromMapOrEmpty(inputQuery, "extensions");

        Map<String, MultipartFile> fileParams = readMultipartFiles(httpServletRequest);

        Map<String, List<String>> fileMappings = Optional.ofNullable(this.<Map<String, List<String>>>deserializePart(httpServletRequest, "map", LIST_PARAMETERIZED_TYPE_REF.getType())).orElse(new HashMap<>());

        fileMappings.forEach((String fileKey, List<String> objectPaths) -> {
            MultipartFile file = fileParams.get(fileKey);
            if (file != null) {
                objectPaths.forEach((String objectPath) -> {
                    MultipartVariableMapper.mapVariable(objectPath, queryVariables, file);
                });
            }
        });

        String query = (String) inputQuery.get("query");
        String opName = (String) inputQuery.get("operationName");

        Map<String, Object> body = new HashMap<>();
        body.put("query", query);
        body.put("operationName", StringUtils.hasText(opName) ? opName : "");
        body.put("variables", queryVariables);
        body.put("extensions", extensions);

        WebGraphQlRequest graphQlRequest = new WebGraphQlRequest(serverRequest.uri(), serverRequest.headers().asHttpHeaders(), body, this.idGenerator.generateId().toString(), LocaleContextHolder.getLocale());

        if (logger.isDebugEnabled()) {
            logger.debug("Executing: " + graphQlRequest);
        }

        Mono<ServerResponse> responseMono = this.graphQlHandler.handleRequest(graphQlRequest).map(response -> {
            if (logger.isDebugEnabled()) {
                logger.debug("Execution complete");
            }
            ServerResponse.BodyBuilder builder = ServerResponse.ok();
            builder.headers(headers -> headers.putAll(response.getResponseHeaders()));
            builder.contentType(selectResponseMediaType(serverRequest));
            return builder.body(response.toMap());
        });

        return ServerResponse.async(responseMono);
    }

    private static class JsonMultipartInputMessage implements HttpInputMessage {

        private final Part part;

        JsonMultipartInputMessage(Part part) {
            this.part = part;
        }

        @Override
        public InputStream getBody() throws IOException {
            return this.part.getInputStream();
        }

        @Override
        public HttpHeaders getHeaders() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return httpHeaders;
        }
    }

    private <T> T deserializePart(HttpServletRequest httpServletRequest, String name, Type type) {
        try {
            Part part = httpServletRequest.getPart(name);
            if (part == null) {
                return null;
            }
            return (T) this.genericHttpMessageConverter.read(type, null, new JsonMultipartInputMessage(part));
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getFromMapOrEmpty(Map<String, Object> input, String key) {
        if (input.containsKey(key)) {
            return (Map<String, Object>) input.get(key);
        } else {
            return new HashMap<>();
        }
    }

    private static Map<String, MultipartFile> readMultipartFiles(HttpServletRequest httpServletRequest) {
        Assert.isInstanceOf(MultipartHttpServletRequest.class, httpServletRequest, "Request should be of type MultipartHttpServletRequest");
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
        return multipartHttpServletRequest.getFileMap();
    }

    private static MediaType selectResponseMediaType(ServerRequest serverRequest) {
        for (MediaType accepted : serverRequest.headers().accept()) {
            if (SUPPORTED_MEDIA_TYPES.contains(accepted)) {
                return accepted;
            }
        }
        return MediaType.APPLICATION_JSON;
    }

}