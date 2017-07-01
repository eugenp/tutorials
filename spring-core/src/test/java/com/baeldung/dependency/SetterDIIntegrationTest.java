package com.baeldung.dependency;

import com.baeldung.setterdi.Config;
import com.baeldung.setterdi.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  loader = AnnotationConfigContextLoader.class,
  classes = Config.class
)
public class SetterDIIntegrationTest {

    @Autowired
    private Movie movie;

    @Test
    public void givenAutowired_WhenSetOnSetters_ThenDependencyResolved() {
        assertNotNull(movie);
        assertEquals("Genre: comedy U Language: English", movie.toString());
    }
}
