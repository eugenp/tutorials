package com.baeldung.graphql.fileupload;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.GraphQLScalarType;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import static com.baeldung.graphql.fileupload.MultipartGraphQlHttpHandler.SUPPORTED_MEDIA_TYPES;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@Configuration
public class MultipartGraphQlWebMvcAutoconfiguration {

    private final FileUploadDataFetcher fileUploadDataFetcher;

    public MultipartGraphQlWebMvcAutoconfiguration(FileUploadDataFetcher fileUploadDataFetcher) {
        this.fileUploadDataFetcher = fileUploadDataFetcher;
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return (builder) -> builder
                .type(newTypeWiring("Mutation").dataFetcher("uploadFile", fileUploadDataFetcher))
                .scalar(GraphQLScalarType.newScalar()
                        .name("Upload")
                        .coercing(new UploadCoercing())
                        .build());
    }

    @Bean
    @Order(1)
    public RouterFunction<ServerResponse> graphQlMultipartRouterFunction(
            GraphQlProperties properties,
            WebGraphQlHandler webGraphQlHandler,
            ObjectMapper objectMapper
    ) {
        String path = properties.getPath();
        RouterFunctions.Builder builder = RouterFunctions.route();
        MultipartGraphQlHttpHandler graphqlMultipartHandler = new MultipartGraphQlHttpHandler(webGraphQlHandler, new MappingJackson2HttpMessageConverter(objectMapper));
        builder = builder.POST(path, RequestPredicates.contentType(MULTIPART_FORM_DATA)
                .and(RequestPredicates.accept(SUPPORTED_MEDIA_TYPES.toArray(new MediaType[]{}))), graphqlMultipartHandler::handleMultipartRequest);
        return builder.build();
    }
}