package org.baeldung.aop;

import org.baeldung.config.TestConfig;
import org.baeldung.dao.FooDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class AopCachingTest {

    @Autowired
    private FooDao dao;

    @Autowired
    SimpleCache cache;

    @Test
    public void givenCachingAspect_whenCallFindById_thenReturnValueIsAddedToCache() {
        dao.findById(1L);
        assertThat(cache.get(1L), notNullValue());
    }
}
