package com.baeldung.beaninjection;

import com.baeldung.beaninjection.domain.Director;
import com.baeldung.beaninjection.domain.Movie;
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
  classes = Config.class)
public class BeanInjectIntegrationTest {

    @Autowired
    private Movie movie;

    @Autowired
    private Director director;

    @Test
    public void whenSetterInjection_ThenValidDependency() {
        assertNotNull(movie);
        assertEquals("Data{title='Clockwork Orange', duration='2h 17m'}", movie.getData().toString());
        assertEquals("Director{name='Stanley', surname='Kubrick'}", movie.getDirector().toString());
    }

    @Test
    public void whenConstructorInjection_ThenValidDependency() {
        assertNotNull(director);
        assertEquals("Director{name='Stanley', surname='Kubrick'}", director.toString());
    }
}
