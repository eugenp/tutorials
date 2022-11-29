package com.baeldung;

import javax.persistence.EntityManager;
import com.baeldung.model.Post;
import com.baeldung.model.Person;
import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spring.data.repository.config.EnableBlazeRepositories;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

abstract class AbstractTest {

    @Configuration
    @ComponentScan("com.baeldung")
    @EnableEntityViews(basePackages = { "com.baeldung.view"})
    @EnableBlazeRepositories(
            basePackages = "com.baeldung.repository")
    static class TestConfig {
    }
}
