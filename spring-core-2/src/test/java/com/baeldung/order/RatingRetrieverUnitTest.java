package com.baeldung.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RatingRetrieverUnitTest {

    @Configuration
    @ComponentScan(basePackages = {"com.baeldung.order"})
    static class ContextConfiguration {
    }

    @Autowired
    private List<Rating> ratings;

    @Test
    public void givenOrderOnComponents_whenInjected_thenAutowireByOrderValue() {
        assertThat(ratings.get(0).getRating()).isEqualTo(1);
        assertThat(ratings.get(1).getRating()).isEqualTo(2);
        assertThat(ratings.get(2).getRating()).isEqualTo(3);
    }

}
