package com.baeldung.spring.cloud.client;

import com.baeldung.spring.cloud.model.Book;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Configuration
@EnableAutoConfiguration
@RestController
public class MockBookServiceConfig {

    @RequestMapping("/books")
    public List<Book> getBooks() {
        return Collections.singletonList(new Book("some title", "some author"));
    }

}
