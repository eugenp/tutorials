package com.baeldung.springbootmvc;

import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.notFound;
import static org.springframework.web.servlet.function.ServerResponse.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import com.baeldung.springbootmvc.ctrl.ProductController;
import com.baeldung.springbootmvc.svc.ProductService;

@SpringBootApplication
public class SpringBootMvcFnApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringBootMvcFnApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcFnApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> productListing(ProductController pc, ProductService ps) {
        return pc.productListing(ps);
    }

    @Bean
    RouterFunction<ServerResponse> allApplicationRoutes(ProductController pc, ProductService ps) {
        return route().add(pc.remainingProductRoutes(ps))
            .before(req -> {
                LOG.info("Found a route which matches " + req.uri()
                    .getPath());
                return req;
            })
            .after((req, res) -> {
                if (res.statusCode() == HttpStatus.OK) {
                    LOG.info("Finished processing request " + req.uri()
                        .getPath());
                } else {
                    LOG.info("There was an error while processing request" + req.uri());
                }
                return res;
            })
            .onError(Throwable.class, (e, res) -> {
                LOG.error("Fatal exception has occurred", e);
                return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            })
            .build()
            .and(route(RequestPredicates.all(), req -> notFound().build()));
    }

    public static class Error {
        private String errorMessage;

        public Error(String message) {
            this.errorMessage = message;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
